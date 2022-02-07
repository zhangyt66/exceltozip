package org.zyt.controller;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.ExcelWriter;
import com.alibaba.excel.write.metadata.WriteSheet;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Async;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.zyt.entity.People;
import org.zyt.util.FileUtil;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Vector;
import java.util.concurrent.atomic.AtomicInteger;

@RestController
public class ExcelController {
    private static final Logger logger = LoggerFactory.getLogger(ExcelController.class);

    @RequestMapping(value = "/demo", method = RequestMethod.GET)
    private void demo1(HttpServletResponse response) {
        Vector<File> ts = new Vector<File>();
        AtomicInteger atomicInteger = new AtomicInteger(0);
        long startTime = System.currentTimeMillis();
        // 这里 指定文件
        logger.info("访问下载接口中...");
        String fileName = "D://" + startTime + "//";
        for (int i = 0; i < 30; i++) {
            asynExcel(fileName, i, atomicInteger,ts);
        }
        while (true) {
            if (atomicInteger.get() == 30) {
                File[] a = {};
                File[] files1 = ts.toArray(a);
                FileUtil.zipFiles(files1,new File("D://"+startTime+"//test.zip"));
                download("D://"+startTime+"//test.zip",response);
                break;
            }
        }
        logger.info("生成了excel,文件位置："+fileName);
        String parent = ts.get(0).getParent();
        boolean b = FileUtil.deleteDirectory(parent);
        if(b){
            logger.info("缓存文件已删除成功");
        }else {
            logger.error("删除异常");
        }
        long endTime = System.currentTimeMillis();
        System.out.println("运行时长"+(endTime-startTime)+"ms");
    }


    public HttpServletResponse download(String path, HttpServletResponse response) {
        try {
            // path是指欲下载的文件的路径。
            File file = new File(path);
            // 取得文件名。
            String filename = file.getName();
            // 取得文件的后缀名。
            String ext = filename.substring(filename.lastIndexOf(".") + 1).toUpperCase();

            // 以流的形式下载文件。
            InputStream fis = new BufferedInputStream(new FileInputStream(path));
            byte[] buffer = new byte[fis.available()];
            fis.read(buffer);
            fis.close();
            // 清空response
            response.reset();
            // 设置response的Header
            response.addHeader("Content-Disposition", "attachment;filename=" + new String(filename.getBytes()));
            response.addHeader("Content-Length", "" + file.length());
            OutputStream toClient = new BufferedOutputStream(response.getOutputStream());
            response.setContentType("application/octet-stream");
            toClient.write(buffer);
            toClient.flush();
            toClient.close();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return response;
    }


    public static  void  writeExecl (List<People> data,String fileName,int num) {
        logger.info(new Date()+"被调用一次");
        ExcelWriter excelWriter = null;
        // 方法2 如果写到不同的sheet 同一个对象
        try {
            excelWriter = EasyExcel.write(FileUtil.createNewFile(fileName), People.class).build();// 去调用写入,这里我调用了五次，实际使用时根据数据库分页的总的页数来。这里最终会写到5个sheet里面
            // 每次都要创建writeSheet 这里注意必须指定sheetNo 而且sheetName必须不一样
            // 分页去数据库查询数据 这里可以去数据库查询每一页的数据
             /*int num2 = data.size()/100000;
             for (int i = 0; i < num2; i++) {
                WriteSheet Sheet = EasyExcel.writerSheet(i, "sheet" + i).build();
                data.subList(i*100000,(i+1)*100000);
                excelWriter.write(data, Sheet);
             }*/
            WriteSheet Sheet = EasyExcel.writerSheet(num, "sheet" + num).build();
            excelWriter.write(data, Sheet);

        } finally {
            // 千万别忘记finish 会帮忙关闭流
            if (excelWriter != null) {
                excelWriter.finish();
            }
        }
    }


    @Async("asyncServiceExecutor")
    public void asynExcel(String fileName, int num, AtomicInteger atomicInteger, Vector<File> ts){
        logger.info("线程"+Thread.currentThread().getName()+"开始执行");
        fileName = fileName+num+".xlsx";
        ts.add(new File(fileName));
        List<People> list = new ArrayList<People>();
        for (int i = 0; i < 400000; i++) {
            People zs = new People().Name("罗z翔电动小马达总公司").Age("33").Sex("0").Height("172").Weight("120").Weight1("121").Weight2("123").Weight3("130");
            list.add(zs);
        }
        writeExecl(list,fileName  ,num);
        int number = atomicInteger.incrementAndGet();
        logger.info("线程"+Thread.currentThread().getName()+"执行结束atomicInteger="+number);
    }

}
