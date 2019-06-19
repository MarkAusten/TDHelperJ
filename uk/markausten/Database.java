package uk.markausten;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

class Database
{
    /**
     * Connect to the specified database.
     */
    private Connection connect(String fileName)
    {
        Connection conn = null;
        String url = "jdbc:sqlite:data/" + fileName;

        try
        {
            conn = DriverManager.getConnection(url);
        }
        catch (SQLException e)
        {
            LogClass.log.severe(e.getMessage());
        }

        return conn;
    }

    /**
     * Connect to a sample database
     *
     * @param fileName the database file name
     */
    void createNewDatabase(String fileName)
    {
        Connection conn = connect(fileName);

        if (conn != null)
        {
            try
            {
                conn.close();
            }
            catch (SQLException e)
            {
                LogClass.log.severe(e.getMessage());
            }
        }
    }

    /**
     * @return A list of commodities.
     */
    List<Object[]> getCommodities()
    {
        StringBuilder sb = new StringBuilder();

        sb
                .append("select c.name as category, i.name as commodity ")
                .append("from category c ")
                .append("join item i on c.category_id = i.category_id ")
                .append("order by c.name, i.ui_order;");

        return runQuery(sb.toString());
    }

    /**
     * @return A list of commodities.
     */
    List<Object[]> getCommoditiesAndShips()
    {
        StringBuilder sb = new StringBuilder();

        sb
                .append("with c1 as ")
                .append("( ")
                .append("select c.name as category, i.name as commodity, 0 as selected ")
                .append("from Category c ")
                .append("join Item i on c.category_id = i.category_id ")
                .append("union ")
                .append("select 'Ship' as Category, s.name as Ship, 0 as selected ")
                .append("from Ship s ")
                .append(") ")
                .append("select category, commodity, selected ")
                .append("from c1 ")
                .append("order by category, commodity;");

        return runQuery(sb.toString());
    }

    /**
     * @return A list of commodities.
     */
    List<Object[]> getRareItems()
    {
        StringBuilder sb = new StringBuilder();

        sb
                .append("select c.name as category, r.name as rare ")
                .append("from category c ")
                .append("join RareItem r on c.category_id = r.category_id ")
                .append("order by c.name, r.name;");

        return runQuery(sb.toString());
    }

    /**
     * @return A list of commodities.
     */
    List<Object[]> getShipVendor(String source)
    {
        String[] data = source.split("/");

        StringBuilder sb = new StringBuilder();

        sb
                .append("select s.name as Ship, s.cost as Cost ")
                .append("from Ship s ")
                .append("join ShipVendor sv on s.ship_id = sv.ship_id ")
                .append("join Station st on sv.station_id = st.station_id ")
                .append("join System sys on st.system_id = sys.system_id ")
                .append("where sys.name = '")
                .append(data[0].trim())
                .append("' and st.name = '")
                .append(data[1].trim())
                .append("' ")
                .append("order by s.cost desc;");

        return runQuery(sb.toString());
    }

    /**
     * @return A list of Ships.
     */
    List<Object[]> getAllAvailableShips()
    {
        StringBuilder sb = new StringBuilder();

        sb
                .append("select 'Ship' as category, s.name as commodity ")
                .append("from ship s ")
                .append("order by s.name;");

        return runQuery(sb.toString());
    }

    /**
     * @return A list of commodities.
     */
    List<Object[]> getSystems()
    {
        StringBuilder sb = new StringBuilder();

        sb
                .append("select c.name as category, r.name as rare ")
                .append("from category c ")
                .append("join RareItem r on c.category_id = r.category_id ")
                .append("order by c.name, r.name;");

        return runQuery(sb.toString());
    }

    /**
     * Run the specified query and return the results.
     *
     * @param sql The query to run.
     * @return The results of the query as a list of String arrays.
     */
    private List<Object[]> runQuery(String sql)
    {
        List<Object[]> results = new ArrayList<>();

        Connection conn = connect("tradedangerous.db");

        if (conn != null)
        {
            try
            {
                Statement stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery(sql);

                ResultSetMetaData meta = rs.getMetaData();

                int colCount = meta.getColumnCount();

                while (rs.next())
                {
                    Object[] record = new Object[colCount];

                    for (int i = 0; i < colCount; ++i)
                    {
                        record[i] = rs.getString(i + 1);
                    }

                    results.add(record);
                }
            }
            catch (SQLException e)
            {
                LogClass.log.severe(e.getMessage());
            }
            finally
            {
                try
                {
                    conn.close();
                }
                catch (Exception e)
                {
                    LogClass.log.severe(e.getMessage());
                }
            }
        }

        return results;
    }

    /**
     * Execute the specified query.
     *
     * @param sql The query to run.
     */
    private void executeQuery(String sql)
    {
        Connection conn = connect("tdhelper.db");

        if (conn != null)
        {
            try
            {
                Statement stmt = conn.createStatement();
                stmt.execute(sql);
            }
            catch (SQLException e)
            {
                LogClass.log.severe(e.getMessage());
            }
            finally
            {
                try
                {
                    conn.close();
                }
                catch (Exception e)
                {
                    LogClass.log.severe(e.getMessage());
                }
            }
        }
    }

    List<Object[]> searchSystem(String fragment)
    {
        StringBuilder sb = new StringBuilder();

        sb
                .append("select sys.name || '/' || stn.name as sys_stn ")
                .append("from system sys ")
                .append("join station stn on stn.system_id = sys.system_id ")
                .append("where sys.name like '")
                .append(fragment)
                .append("%'")
                .append("order by sys.name, stn.name");

        return runQuery(sb.toString());
    }

    /**
     * Check to see if the ships table is in the tdh database and create it if not.
     */
    void createShipsTableIfNotExists()
    {
        StringBuilder sb = new StringBuilder();

        sb
                .append("create table if not exists Ships ( ")
                .append("id integer primary key autoincrement, ")
                .append("ship_id integer")
                .append("name varchar(100), ")
                .append("type varchar(100), ")
                .append("capacity integer, ")
                .append("laden decimal(10,2), ")
                .append("unladen decimal(10,2), ")
                .append("is_base tinyint)");

        executeQuery(sb.toString());
    }

    /**
     * Check to see if the ships table is in the tdh database and create it if not.
     */
    void createNetLogsTableIfNotExists()
    {
        StringBuilder sb = new StringBuilder();

        sb
                .append("create table if not exists NetLogs ( ")
                .append("Filename nvarchar primary key, ")
                .append("HasSystems tinyint, ")
                .append("HeaderTimestamp nvarchar)");

        executeQuery(sb.toString());

        String sql = "create index if not exists FileHasSystems on NetLogs (HasSystems)";

        executeQuery(sql);
    }
}
