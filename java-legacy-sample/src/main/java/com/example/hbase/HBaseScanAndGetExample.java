package com.example.hbase;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.CompareOperator;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.TableName;
import org.apache.hadoop.hbase.client.*;
import org.apache.hadoop.hbase.filter.BinaryComparator;
import org.apache.hadoop.hbase.filter.FamilyFilter;
import org.apache.hadoop.hbase.filter.FilterList;
import org.apache.hadoop.hbase.filter.KeyOnlyFilter;
import org.apache.hadoop.hbase.util.Bytes;

import java.io.IOException;
import java.util.Map;
import java.util.NavigableMap;

/**
 *
 */
public class HBaseScanAndGetExample {

    private final Table table;

    public HBaseScanAndGetExample(Configuration configuration) throws IOException {
        // HTable 은 Deprecated 되었기 때문에 Connection 을 사용해야 한다.
        Connection connection = ConnectionFactory.createConnection(configuration);
        this.table = connection.getTable(TableName.valueOf("table-name"));
    }

    /**
     * rowKey 로 get 하는 예제
     *
     * @param rowKey HBase RowKey
     * @return Result
     * @throws IOException
     */
    public Result getItem(String rowKey) throws IOException {
        Get get = new Get(Bytes.toBytes(rowKey));

        // 필요하다면 조건을 추가할 수 있다. 추가한 조건에 맞는 데이터만 가져온다.
        // family 만 추가하거나, qualifier 도 추가하거나
        get.addFamily(Bytes.toBytes("family"));
        get.addColumn(Bytes.toBytes("family"), Bytes.toBytes("qualifier"));

        return table.get(get);
    }

    public ResultScanner scanItem() throws IOException {
        Scan scan = new Scan();

        // 필요하다면 filter 조건을 추가한다.
        FilterList filterList = new FilterList();
        filterList.addFilter(new KeyOnlyFilter());
        filterList.addFilter(new FamilyFilter(CompareOperator.EQUAL, new BinaryComparator(Bytes.toBytes("family"))));

        scan.setFilter(filterList);
        return table.getScanner(scan);
    }

    public static void main(String[] args) throws IOException {
        Configuration configuration = HBaseConfiguration.create();
        // Option1. resource 파일을 만들어서 추가
        configuration.addResource("resource-file");
        // Option2. zookeeper quorum 주입
        configuration.set("hbase.zookeeper.quorum", "q1,q2,q3");


        HBaseScanAndGetExample example = new HBaseScanAndGetExample(configuration);

        // sample 이라는 rowkey 로 result 값을 가져왔다.
        // rowkey 컬럼 데이터 찾는 부분은 아래 처럼 진행할 수 있다.

        Result result = example.getItem("sample");
        // 1. 특정 family 에 해당하는 모든 value 가져오기
        NavigableMap<byte[], byte[]> map = result.getFamilyMap(Bytes.toBytes("family"));
        for (Map.Entry<byte[], byte[]> mapEntry : map.entrySet()) {
            String key = new String(mapEntry.getKey());
            String value = new String(mapEntry.getValue());
            System.out.println("Column qualifier: " + key);
            System.out.println("Value: " + value);
        }

        // 2. 특정 family, qualifier 에 해당하는 value 가져오기
        byte[] cell = result.getValue(Bytes.toBytes("family"), Bytes.toBytes("qualifier"));

        // 3. 전체 가져오기
        NavigableMap<byte[], NavigableMap<byte[], NavigableMap<Long, byte[]>>> data = result.getMap();
        for (Map.Entry<byte[], NavigableMap<byte[], NavigableMap<Long, byte[]>>> navigableMapEntry : data.entrySet()) {
            NavigableMap<byte[], NavigableMap<Long, byte[]>> familyContents = navigableMapEntry.getValue();
            for (Map.Entry<byte[], NavigableMap<Long, byte[]>> mapEntry : familyContents.entrySet()) {
                String a = Bytes.toString(mapEntry.getKey());
                NavigableMap<Long, byte[]> v = mapEntry.getValue();
                for (Long l : v.keySet()) {
                    System.out.println(a + ":" + Bytes.toString(v.get(l)));
                }

            }
        }
    }
}
