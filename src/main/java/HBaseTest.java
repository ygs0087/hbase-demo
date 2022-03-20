import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.*;
import org.apache.hadoop.hbase.client.*;
import org.apache.hadoop.hbase.util.Bytes;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.Arrays;

public class HBaseTest {

    public static Logger logger = LoggerFactory.getLogger(HBaseTest.class);

    private static final String MY_NAMESPACE_NAME = "ygs";
    private static final String MY_TABLE = "student";
    private static final String COL_FAMILY_INFO = "info";
    private static final String COL_FAMILY_SCORE = "score";

    private static Admin admin;
    private static Connection connection;

    public static void main(String[] args) throws Exception {
        Configuration configuration = HBaseConfiguration.create();
        configuration.set("hbase.zookeeper.quorum", "emr-worker-2,emr-worker-1,emr-header-1");
        configuration.set("hbase.zookeeper.property.clientPort", "2181");
        connection = ConnectionFactory.createConnection(configuration);
        admin = connection.getAdmin();

        // create namespace
        if (!namespaceExists(admin, MY_NAMESPACE_NAME)) {
            logger.info("Creating Namespace [" + MY_NAMESPACE_NAME + "].");
            admin.createNamespace(NamespaceDescriptor
                    .create(MY_NAMESPACE_NAME).build());
            logger.info("Namespace created successfully!");
        }

        logger.info("Check if Namespace exists now:"+ namespaceExists(admin, MY_NAMESPACE_NAME));

        TableName tableName = TableName.valueOf(MY_NAMESPACE_NAME+":"+MY_TABLE);

        // 建表
        createTable(tableName, COL_FAMILY_INFO, COL_FAMILY_SCORE);

        String[][] data = {
                {"Tom","20210000000001","1","75","82"},
                {"Jerry","20210000000002","1","85","67"},
                {"Jack","20210000000003","2","80","80"},
                {"Rose","20210000000004","2","60","61"},
                {"阳广穗","G20220735020031","1","90","90"}
        };

        logger.info("-----------------------------------------");
        logger.info("Insertion");
        logger.info("-----------------------------------------");
        // 插入数据
        initData(data, tableName);

        scanTable(tableName);

        logger.info("-----------------------------------------");
        logger.info("Delele Data");
        logger.info("-----------------------------------------");

        // 删除数据
        for (String[] rowKey : data) {
            // 指定rowKey
            Delete delete = new Delete(Bytes.toBytes(rowKey[0]));
            connection.getTable(tableName).delete(delete);
            logger.info("Delete Success for rowkey : {} ", rowKey[0]);
        }

        scanTable(tableName);

        logger.info("-----------------------------------------");
        logger.info("Delete Table");
        logger.info("-----------------------------------------");
        // // 删除表
        if (admin.tableExists(tableName)) {
            admin.disableTable(tableName);
            admin.deleteTable(tableName);
            logger.info("Table Delete Successful");
        } else {
            logger.info("Table does not exist!");
        }
        logger.info("-----------------------------------------");

        logger.info("Check if table exist: {}", admin.tableExists(tableName));

        //Delete namespace
        logger.info("-----------------------------------------");
        admin.deleteNamespace(MY_NAMESPACE_NAME);
        logger.info("Namaspace Deleted successfully.");
        logger.info("Check if Namaspace Exist: {}", namespaceExists(admin,MY_NAMESPACE_NAME));


    }

    private static boolean createTable(TableName tableName, String... columnFamilies) {
        if (tableName == null || columnFamilies.length < 1) {
            throw new IllegalArgumentException("tableName or column cannot be null");
        }

        try {
            if (admin.tableExists(tableName)) {
                logger.info("Table already exists");
                return false;
            }

            TableDescriptorBuilder tableDescriptorBuilder = TableDescriptorBuilder.newBuilder(tableName);
            for (String columnFamily : columnFamilies) {
                ColumnFamilyDescriptor columnFamilyDescriptor = ColumnFamilyDescriptorBuilder.newBuilder(Bytes.toBytes(columnFamily)).build();
                tableDescriptorBuilder.setColumnFamily(columnFamilyDescriptor);
            }

            admin.createTable(tableDescriptorBuilder.build());
            logger.info("create table success, tableName: {}", tableName.getNameAsString());
            return true;
        } catch (Exception e) {
            logger.error("create table failed, tableName: {}", tableName.getNameAsString(), e);
        }

        return false;
    }

    private static void initData(String[][] data, TableName tableName) {
        if (data == null || data.length == 0 || data[0].length == 0) {
            logger.error("init data invalid, data: {}", data);
        }

        Table table = null;
        try {
            table = connection.getTable(tableName);
            for (String[] rowData : data) {
                addData(table, rowData[0], COL_FAMILY_INFO, "student_id", rowData[1]);
                addData(table, rowData[0], COL_FAMILY_INFO, "class", rowData[2]);
                addData(table, rowData[0], COL_FAMILY_SCORE, "understanding", rowData[3]);
                addData(table, rowData[0], COL_FAMILY_SCORE, "programming", rowData[4]);
                logger.info("Data insert success for rowKey:" + rowData[0]);
            }

        } catch (Exception e) {
            logger.error("init data error", e);
        } finally {
            if (table != null) {
                try {
                    table.close();
                } catch (IOException e) {
                    logger.error("table connection close error", e);
                }
            }
        }
    }

    private static void addData(Table table, String rowKey, String colFamily, String colKey, String colValue) {
        try {
            Put put = new Put(Bytes.toBytes(rowKey));
            put.addColumn(Bytes.toBytes(colFamily), Bytes.toBytes(colKey), Bytes.toBytes(colValue));
            table.put(put);
        } catch (IOException e) {
            throw new IllegalArgumentException(e);
        }
    }

    private static void scanTable(TableName tableName) throws IOException {

        logger.info("-----------------------------------------");
        logger.info("Scanning Table");
        logger.info("-----------------------------------------");
        Scan scan = new Scan();
        ResultScanner resultScan = connection.getTable(tableName).getScanner(scan);
        int count = 0;
        for(Result rs : resultScan) {

            String rowKey = Bytes.toString(rs.getRow());
            logger.info("row key: {}", rowKey);
            Cell[] cells  = rs.rawCells();
            for(Cell cell : cells) {

                logger.info(Bytes.toString(cell.getFamilyArray(), cell.getFamilyOffset(), cell.getFamilyLength()) + "."+
                        Bytes.toString(cell.getQualifierArray(), cell.getQualifierOffset(), cell.getQualifierLength())+"::"+
                        Bytes.toString(cell.getValueArray(), cell.getValueOffset(), cell.getValueLength()));
                count++;
            }
            logger.info("-----------------------------------------");
        }

        if (count == 0){
            logger.info("No row found!");
        }
        //closing the scanner
        resultScan.close();
        logger.info("-----------------------------------------");
    }

    //check if namespace Exists
    private static boolean namespaceExists(Admin admin,String name) throws IOException{
        NamespaceDescriptor[] list = admin.listNamespaceDescriptors();
        logger.info("Current namespace : "+ Arrays.toString(list));

        for (NamespaceDescriptor namespaceDescriptor : list) {
            if(namespaceDescriptor.getName().equals(name)){
                return true;
            }
        }
        return false;
    }

}
