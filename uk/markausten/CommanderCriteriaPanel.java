package uk.markausten;

import org.apache.commons.io.FileUtils;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.FileReader;
import java.util.List;

import static java.nio.charset.Charset.forName;

class CommanderCriteriaPanel extends BaseCriteriaPanel
{
    private MainPanel ui;

    private JLabel lblHint = new JLabel(Constants.LABEL_CAPTION_COMMANDER_HINT);

    /**
     * constructor.
     */
    CommanderCriteriaPanel(MainPanel ui)
    {
        super();

        this.ui = ui;

        initGui();
        initEdApi();
    }

    /**
     * @param ship The object continaing the ship data.
     * @return The current capacity of the ship.
     */
    private int calculateShipCapacity(JSONObject ship)
    {
        int capacity = 0;

        JSONObject modules = (JSONObject) ship.get("modules");

        for (Object o : modules.keySet())
        {
            String key = (String) o;

            if (key
                    .toLowerCase()
                    .startsWith("slot"))
            {
                String k = key + ".module";

                JSONObject module = Utils.getJsonObject(modules, k);

                if (module != null)
                {
                    String name = (String) module.get("name");

                    if (name
                            .toLowerCase()
                            .startsWith("int_cargorack_size"))
                    {
                        int size = Utils.convertStringToInt(name.substring(18, 19));

                        capacity += (int) Math.pow(2, size);
                    }
                }
            }
        }

        return capacity;
    }

    @Override
    public List<String> getCommand(List<String> command)
    {
        command.add("import");
        command.add("-P");
        command.add("edapi");
        command.add("-O");
        command.add("tdh");

        return command;
    }

    /**
     * @param ship The ship  object.
     * @return The values of the ship.
     */
    private long getShipCost(JSONObject ship)
    {
        JSONObject value = Utils.getJsonObject(ship, "value");

        long hullCost = (long) value.get("hull");
        long moduleCost = (long) value.get("modules");

        return hullCost + moduleCost;
    }

    /**
     * @param ship The json object containing the ship data.
     * @return The ship name.
     */
    private String getShipName(JSONObject ship)
    {
        String name;

        if (ship == null)
        {
            name = "unknown ship";
        }
        else
        {
            // Retrieve the ship ID if it exists. This is the commander specified ID not the FDev ID.
            String shipId = (String) ship.get("shipID");

            // If the commander has not specified an ID then use the FDev ID.
            if (shipId == null)
            {
                shipId = ship
                        .get("id")
                        .toString();
            }

            // Get the commander specified ship name if it exists.
            String shipName = (String) ship.get("shipName");

            if (shipName == null)
            {
                shipName = "";
            }
            else
            {
                shipName = "(" + shipName + ")";
            }

            // Get the ship type inconveniently called "name" by FDev.
            String shipType = (String) ship.get("name");

            // Construct a string from the extracted data.
            name = translateShipType(shipType) + " " + shipId + " " + shipName;
        }

        return name;
    }

    private String getShipPads(String shipType)
    {
        String pads = "";

        JSONObject ship = Utils.getBaseShipData(shipType);

        if (ship != null)
        {
            pads = (String) ship.get("padsizes");
        }

        return pads;
    }

    private void initEdApi()
    {
//        // Check to see if there is an edapi.config file.
//        File edapi = new File("data/edapi.config");
//
//        if (!edapi.exists())
//        {
//            // One does not exist so write a skeleton file.
//            String text = "[companion]\nredirect_uri = http://127.0.0.1:2989/callback\n";
//
//            try
//            {
//                FileUtils.writeStringToFile(edapi, text, forName("UTF-8"));
//            }
//            catch (Exception e)
//            {
//                LogClass.log.severe(e.getMessage());
//            }
//        }
    }

    /**
     * Set up the panel.
     */
    private void initGui()
    {
        setBorder(BorderFactory.createMatteBorder(0, 1, 0, 0, Color.WHITE));

        setLayout(new GridBagLayout());

        GridBagConstraints gbc = new GridBagConstraints();

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.WEST;

        this.add(lblHint, gbc);
    }

    @Override
    public void postProcessingHook()
    {
        try
        {
            // Try to open the downloaded profile json file.
            String path = System.getProperty("user.dir");
            String fileName = path + "/tmp/tdh_profile.json";
            File jsonFile = new File(fileName);

            if (jsonFile.exists())
            {
                Settings p = TDGUI.settings;

                // Parse the file and extract the required data.
                JSONObject json = (JSONObject) (new JSONParser().parse(new FileReader(fileName)));

                JSONObject profile = Utils.getJsonObject(json, "profile");
                JSONObject commander = Utils.getJsonObject(profile, "commander");
                JSONObject ship = Utils.getJsonObject(profile, "ship");

                float rebuy = p.settingsRebuy;
                int insurance = 0;

                if (rebuy > 0f)
                {
                    long cost = getShipCost(ship);

                    insurance = (int) (cost / 100 * rebuy);
                }

                p.shipInsurance = insurance;
                p.commanderName = (String) commander.get("name");
                p.commanderCredits = (long) commander.get("credits");
                p.shipId = (long) ship.get("id");
                p.shipSelected = getShipName(ship);
                p.shipCapacity = calculateShipCapacity(ship);
                p.shipPads = getShipPads(p.shipSelected.split(" ")[0]);

                updateOwnedShips(profile);

                ui.saveSettings();
                ui.refreshShipPanel();
            }
        }
        catch (Exception e)
        {
            LogClass.log.severe(e.getMessage());
        }
    }

    @Override
    public boolean processCanBeCancelled()
    {
        return false;
    }

    @Override
    String requiredButtonMode()
    {
        return Constants.REQUIRED_BUTTON_MODE_COMMANDER;
    }

    /**
     * @param shipType The internal ship type as specified by FDev.
     * @return The stip type as seen in the game.
     */
    private String translateShipType(String shipType)
    {
        String type = "";

        JSONObject ship = Utils.getBaseShipData(shipType);

        if (ship != null)
        {
            type = (String) ship.get("name");
        }

        return type;
    }

    /**
     * Update the owned ships on record if anything has changed.
     *
     * @param profile The downloaded profile object.
     */
    private void updateOwnedShips(JSONObject profile)
    {
        JSONObject ships = Utils.getJsonObject(profile, "ships");

        for (Object o : ships.keySet())
        {
            String shipId = (String) o;

            JSONObject ship = Utils.getJsonObject(ships, shipId);

            if (ship != null)
            {
                long cost = getShipCost(ship);
                String shipName = getShipName(ship);

                String tmp = "break;";
            }
        }
    }
}

class JsonO extends JSONObject
{
    public JsonO getJso(String key)
    {
        JsonO result = null;

        for (String k : key.split("\\."))
        {
            result = (JsonO) get(k);

            if (result == null)
            {
                break;
            }
        }

        return result;

    }
}