package excel;

import com.alibaba.excel.EasyExcel;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

public class TestEasyExcel {
    private static final String filePath = "F:\\writeTest.xlsx";
    @Test
    public void writeExcel(){
        EasyExcel.write(filePath,DemoData.class).sheet("学生列表").doWrite(ListInfo());
    }

    @Test
    public void readExcel(){
        EasyExcel.read(filePath,DemoData.class,new ExcelListener()).sheet().doRead();
    }


    public static List<DemoData> ListInfo(){
        List<DemoData> List = new ArrayList<DemoData>();
        for(int i = 0;i<10;i++){
            DemoData demoData = new DemoData();
            demoData.setSno(i);
            demoData.setName("jack"+i);
            List.add(demoData);
        }
        return List;
    }


}
