package uk.markausten;

import java.util.prefs.Preferences;

class Settings
{
    boolean buyBlackMarket;
    boolean buyOneStop;
    boolean localBlackMarket;
    boolean localOutfitting;
    boolean localRearm;
    boolean localRefuel;
    boolean localRepair;
    boolean localShipyard;
    boolean localStations;
    boolean localTrading;
    boolean navFull;
    boolean navStations;
    boolean oldDataShortest;
    boolean raresQuiet;
    boolean raresReverse;
    boolean runBlackMarket;
    boolean runDirect;
    boolean runInsurance;
    boolean runLoop;
    boolean runShorten;
    boolean runShowJumps;
    boolean runTowards;
    boolean runUnique;
    boolean sellBlackMarket;

    float buyNearLy;
    float localMaxLy;
    float navJumpRangeLy;
    float oldDataNearLy;
    float raresAwayLy;
    float raresSearchDistanceLy;
    float runLsPenalty;
    float runMargin;
    float sellNearLy;
    float shipLaden;
    float shipUnladen;

    int buyAbove;
    int buyBelow;
    int buyResults;
    int buySupply;
    int navRefuel;
    int oldDataAge;
    int oldDataMaxLs;
    int oldDataResults;
    int raresLimit;
    int runAge;
    int runCargoLimit;
    int runDemand;
    int runEndJumps;
    int runHops;
    int runJumps;
    int runLoopInt;
    int runMaxGpt;
    int runMaxLs;
    int runMinGpt;
    int runPruneHops;
    int runPruneScore;
    int runRoutes;
    int runStartJumps;
    int runStock;
    int sellAbove;
    int sellBelow;
    int sellResults;
    int shipCapacity;
    int shipInsurance;
    long commanderCredits;

    String buyAvoid;
    String buyCommodity;
    String buyPads;
    String buyPlanetary;
    String buySortBy;
    String currentDestination;
    String currentSource;
    String localPads;
    String localPlanetary;
    String markedSystems;
    String navAvoid;
    String navPads;
    String navPlanetary;
    String navVia;
    String oldDataPads;
    String oldDataPlanetary;
    String raresFrom;
    String raresPads;
    String raresPlanetary;
    String raresRare;
    String raresRareType;
    String raresSortBy;
    String runAvoid;
    String runPlanetary;
    String runVia;
    String selectedShip;
    String sellAvoid;
    String sellCommodity;
    String sellPads;
    String sellPlanetary;
    String sellSortBy;
    String shipPads;

    Settings()
    {
        setDefaultSettings();
        loadSettings();
    }

    /**
     * Clear all the settings and set the defaults.
     */
    private void clearSettings()
    {
        Preferences prefs = getPreferences();

        try
        {
            prefs.clear();
            setDefaultSettings();
        }
        catch (Exception e)
        {
            LogClass.log.severe(e.getMessage());
        }
    }

    /**
     * Get the Preferences object.
     *
     * @return The Preferences object.
     */
    private Preferences getPreferences()
    {
        return Preferences
                .userRoot()
                .node(TDGUI.class.getSimpleName());
    }

    /**
     * Load the settings from the backing store.
     */
    void loadSettings()
    {
        Preferences prefs = getPreferences();

        buyAbove = prefs.getInt("buyAbove", 0);
        buyAvoid = prefs.get("buyAvoid", "");
        buyBelow = prefs.getInt("buyBelow", 0);
        buyBlackMarket = prefs.getBoolean("buyBlackMarket", false);
        buyCommodity = prefs.get("buyCommodity", "");
        buyNearLy = prefs.getFloat("buyNearLy", 0f);
        buyOneStop = prefs.getBoolean("buyOneStop", false);
        buyPads = prefs.get("buyPads", "");
        buyPlanetary = prefs.get("buyPlanetary", "");
        buyResults = prefs.getInt("buyResults", 0);
        buySortBy = prefs.get("buySortBy", "");
        buySupply = prefs.getInt("buySupply", 0);

        commanderCredits = prefs.getLong("commanderCredits", 0);
        currentDestination = prefs.get("currentDestination", "");
        currentSource = prefs.get("currentSource", "");
        markedSystems = prefs.get("markedSystems", "");

        localBlackMarket = prefs.getBoolean("localBlackMarket", false);
        localMaxLy = prefs.getFloat("localMaxLy", 0f);
        localOutfitting = prefs.getBoolean("localOutfitting", false);
        localPads = prefs.get("localPads", "");
        localPlanetary = prefs.get("localPlanetary", "");
        localRearm = prefs.getBoolean("localRearm", false);
        localRefuel = prefs.getBoolean("localRefuel", false);
        localRepair = prefs.getBoolean("localRepair", false);
        localShipyard = prefs.getBoolean("localShipyard", false);
        localStations = prefs.getBoolean("localStations", false);
        localTrading = prefs.getBoolean("localTrading", false);

        navAvoid = prefs.get("navAvoid", "");
        navFull = prefs.getBoolean("navFull", false);
        navJumpRangeLy = prefs.getFloat("navJumpRangeLy", 0f);
        navPads = prefs.get("navPads", "");
        navPlanetary = prefs.get("navPlanetary", "");
        navRefuel = prefs.getInt("navRefuel", 0);
        navStations = prefs.getBoolean("navStations", false);
        navVia = prefs.get("navVia", "");

        raresAwayLy = prefs.getFloat("raresAwayLy", 0f);
        raresFrom = prefs.get("raresFrom", "");
        raresLimit = prefs.getInt("raresLimit", 0);
        raresPads = prefs.get("raresPads", "");
        raresPlanetary = prefs.get("raresPlanetary", "");
        raresQuiet = prefs.getBoolean("raresQuiet", false);
        raresRare = prefs.get("raresRare", "");
        raresRareType = prefs.get("raresRareType", "");
        raresReverse = prefs.getBoolean("raresReverse", false);
        raresSearchDistanceLy = prefs.getFloat("raresSearchDistanceLy", 0f);
        raresSortBy = prefs.get("raresSortBy", "");

        runAge = prefs.getInt("runAge", 0);
        runAvoid = prefs.get("runAvoid", "");
        runBlackMarket = prefs.getBoolean("runBlackMarket", false);
        runCargoLimit = prefs.getInt("runCargoLimit", 0);
        runDemand = prefs.getInt("runDemand", 0);
        runDirect = prefs.getBoolean("runDirect", false);
        runEndJumps = prefs.getInt("runEndJumps", 0);
        runHops = prefs.getInt("runHops", 0);
        runInsurance = prefs.getBoolean("runInsurance", false);
        runJumps = prefs.getInt("runJumps", 0);
        runLoop = prefs.getBoolean("runLoop", false);
        runLoopInt = prefs.getInt("runLoopInt", 0);
        runLsPenalty = prefs.getFloat("runLsPenalty", 0f);
        runMargin = prefs.getFloat("runMargin", 0f);
        runMaxGpt = prefs.getInt("runMaxGpt", 0);
        runMaxLs = prefs.getInt("runMaxLs", 0);
        runMinGpt = prefs.getInt("runMinGpt", 0);
        runPlanetary = prefs.get("runPlanetary", "");
        runPruneHops = prefs.getInt("runPruneHops", 0);
        runPruneScore = prefs.getInt("runPruneScore", 0);
        runRoutes = prefs.getInt("runRoutes", 0);
        runShorten = prefs.getBoolean("runShorten", false);
        runShowJumps = prefs.getBoolean("runShowJumps", false);
        runStartJumps = prefs.getInt("runStartJumps", 0);
        runStock = prefs.getInt("runStock", 0);
        runTowards = prefs.getBoolean("runTowards", false);
        runUnique = prefs.getBoolean("runUnique", false);
        runVia = prefs.get("runVia", "");

        selectedShip = prefs.get("selectedShip", "");
        sellAbove = prefs.getInt("sellAbove", 0);
        sellAvoid = prefs.get("sellAvoid", "");
        sellBelow = prefs.getInt("sellBelow", 0);
        sellBlackMarket = prefs.getBoolean("sellBlackMarket", false);
        sellCommodity = prefs.get("sellCommodity", "");
        sellNearLy = prefs.getFloat("sellNearLy", 0f);
        sellPads = prefs.get("sellPads", "");
        sellPlanetary = prefs.get("sellPlanetary", "");
        sellResults = prefs.getInt("sellResults", 0);
        sellSortBy = prefs.get("sellSortBy", "");

        shipCapacity = prefs.getInt("shipCapacity", 0);
        shipInsurance = prefs.getInt("shipInsurance", 0);
        shipLaden = prefs.getFloat("shipLaden", 0f);
        shipPads = prefs.get("shipPads", "");
        shipUnladen = prefs.getFloat("shipUnladen", 0f);
    }

    /**
     * save the settings to the backing store.
     */
    void saveSettings()
    {
        Preferences prefs = getPreferences();

        prefs.put("buyAvoid", buyAvoid);
        prefs.put("buyCommodity", buyCommodity);
        prefs.put("buyPads", buyPads);
        prefs.put("buyPlanetary", buyPlanetary);
        prefs.put("buySortBy", buySortBy);
        prefs.put("currentDestination", currentDestination);
        prefs.put("currentSource", currentSource);
        prefs.put("localPads", localPads);
        prefs.put("localPlanetary", localPlanetary);
        prefs.put("navAvoid", navAvoid);
        prefs.put("navPads", navPads);
        prefs.put("navPlanetary", navPlanetary);
        prefs.put("navVia", navVia);
        prefs.put("raresFrom", raresFrom);
        prefs.put("raresPads", raresPads);
        prefs.put("raresPlanetary", raresPlanetary);
        prefs.put("raresRare", raresRare);
        prefs.put("raresRareType", raresRareType);
        prefs.put("raresSortBy", raresSortBy);
        prefs.put("runAvoid", runAvoid);
        prefs.put("runPlanetary", runPlanetary);
        prefs.put("runVia", runVia);
        prefs.put("selectedShip", selectedShip);
        prefs.put("sellAvoid", sellAvoid);
        prefs.put("sellCommodity", sellCommodity);
        prefs.put("sellPads", sellPads);
        prefs.put("sellPlanetary", sellPlanetary);
        prefs.put("sellSortBy", sellSortBy);
        prefs.put("shipPads", shipPads);

        prefs.putBoolean("buyBlackMarket", buyBlackMarket);
        prefs.putBoolean("buyOneStop", buyOneStop);
        prefs.putBoolean("localBlackMarket", localBlackMarket);
        prefs.putBoolean("localOutfitting", localOutfitting);
        prefs.putBoolean("localRearm", localRearm);
        prefs.putBoolean("localRefuel", localRefuel);
        prefs.putBoolean("localRepair", localRepair);
        prefs.putBoolean("localShipyard", localShipyard);
        prefs.putBoolean("localStations", localStations);
        prefs.putBoolean("localTrading", localTrading);
        prefs.putBoolean("navFull", navFull);
        prefs.putBoolean("navStations", navStations);
        prefs.putBoolean("raresQuiet", raresQuiet);
        prefs.putBoolean("raresReverse", raresReverse);
        prefs.putBoolean("runBlackMarket", runBlackMarket);
        prefs.putBoolean("runDirect", runDirect);
        prefs.putBoolean("runInsurance", runInsurance);
        prefs.putBoolean("runLoop", runLoop);
        prefs.putBoolean("runShorten", runShorten);
        prefs.putBoolean("runShowJumps", runShowJumps);
        prefs.putBoolean("runTowards", runTowards);
        prefs.putBoolean("runUnique", runUnique);
        prefs.putBoolean("sellBlackMarket", sellBlackMarket);

        prefs.putFloat("buyNearLy", buyNearLy);
        prefs.putFloat("localMaxLy", localMaxLy);
        prefs.putFloat("navJumpRangeLy", navJumpRangeLy);
        prefs.putFloat("raresAwayLy", raresAwayLy);
        prefs.putFloat("raresSearchDistanceLy", raresSearchDistanceLy);
        prefs.putFloat("runLsPenalty", runLsPenalty);
        prefs.putFloat("runMargin", runMargin);
        prefs.putFloat("sellNearLy", sellNearLy);
        prefs.putFloat("shipLaden", shipLaden);
        prefs.putFloat("shipUnladen", shipUnladen);

        prefs.putInt("buyAbove", buyAbove);
        prefs.putInt("buyBelow", buyBelow);
        prefs.putInt("buyResults", buyResults);
        prefs.putInt("buySupply", buySupply);
        prefs.putInt("navRefuel", navRefuel);
        prefs.putInt("raresLimit", raresLimit);
        prefs.putInt("runAge", runAge);
        prefs.putInt("runCargoLimit", runCargoLimit);
        prefs.putInt("runDemand", runDemand);
        prefs.putInt("runEndJumps", runEndJumps);
        prefs.putInt("runHops", runHops);
        prefs.putInt("runJumps", runJumps);
        prefs.putInt("runLoopInt", runLoopInt);
        prefs.putInt("runMaxGpt", runMaxGpt);
        prefs.putInt("runMaxLs", runMaxLs);
        prefs.putInt("runMinGpt", runMinGpt);
        prefs.putInt("runPruneHops", runPruneHops);
        prefs.putInt("runPruneScore", runPruneScore);
        prefs.putInt("runRoutes", runRoutes);
        prefs.putInt("runStartJumps", runStartJumps);
        prefs.putInt("runStock", runStock);
        prefs.putInt("sellAbove", sellAbove);
        prefs.putInt("sellBelow", sellBelow);
        prefs.putInt("sellResults", sellResults);
        prefs.putInt("shipCapacity", shipCapacity);
        prefs.putInt("shipInsurance", shipInsurance);

        prefs.putLong("commanderCredits", commanderCredits);
        prefs.put("markedSystems", markedSystems);
    }

    private void setDefaultSettings()
    {
        buyAbove = 0;
        buyAvoid = "";
        buyBelow = 0;
        buyBlackMarket = false;
        buyCommodity = "";
        buyNearLy = 0f;
        buyOneStop = false;
        buyPads = "";
        buyPlanetary = "";
        buyResults = 0;
        buySortBy = "";
        buySupply = 0;

        commanderCredits = 0L;
        currentDestination = "";
        currentSource = "";
        markedSystems = "";

        localBlackMarket = false;
        localMaxLy = 0f;
        localOutfitting = false;
        localPads = "";
        localPlanetary = "";
        localRearm = false;
        localRefuel = false;
        localRepair = false;
        localShipyard = false;
        localStations = false;
        localTrading = false;

        navAvoid = "";
        navFull = false;
        navJumpRangeLy = 0f;
        navPads = "";
        navPlanetary = "";
        navRefuel = 0;
        navStations = false;
        navVia = "";

        raresAwayLy = 0f;
        raresFrom = "";
        raresLimit = 0;
        raresPads = "";
        raresPlanetary = "";
        raresQuiet = false;
        raresRare = "";
        raresRareType = "";
        raresReverse = false;
        raresSearchDistanceLy = 0f;
        raresSortBy = "";

        runAge = 0;
        runAvoid = "";
        runBlackMarket = false;
        runCargoLimit = 0;
        runDemand = 0;
        runDirect = false;
        runEndJumps = 0;
        runHops = 2;
        runInsurance = false;
        runJumps = 1;
        runLoop = false;
        runLoopInt = 0;
        runLsPenalty = 12.5f;
        runMargin = 0;
        runMaxGpt = 0;
        runMaxLs = 0;
        runMinGpt = 0;
        runPlanetary = "";
        runPruneHops = 0;
        runPruneScore = 0;
        runRoutes = 0;
        runShorten = false;
        runShowJumps = false;
        runStartJumps = 0;
        runStock = 0;
        runTowards = false;
        runUnique = false;
        runVia = "";

        selectedShip = "";

        sellAbove = 0;
        sellAvoid = "";
        sellBelow = 0;
        sellBlackMarket = false;
        sellCommodity = "";
        sellNearLy = 0f;
        sellPads = "";
        sellPlanetary = "";
        sellResults = 0;
        sellSortBy = "";

        shipCapacity = 0;
        shipInsurance = 0;
        shipLaden = 0f;
        shipPads = "";
        shipUnladen = 0f;
    }
}
