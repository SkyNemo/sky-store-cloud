package cn.edu.kmust.store.utils;



import java.io.File;
import java.io.IOException;

import com.trilead.ssh2.Connection;
import com.trilead.ssh2.SCPClient;

public class SSHUtil {



    /*
     * 命令成功执行
     */
    public static final String SUCCESS = "SUCCESS";

    /**
     * 从远程服务器下载文件到本地文件夹
     *
     * @param host
     * @param username
     * @param password
     * @param romoteFileName
     * @param localDir
     */
    public static String getFileFromRemote(String host, String username, String password, String romoteFileName,
                                           String localDir) {
        String msg = SSHUtil.SUCCESS;
        try {
            Connection conn = new Connection(host);
            conn.connect();
            boolean isAuthenticated = conn.authenticateWithPassword(username, password);
            if (isAuthenticated == false)
                return "权限不够!";
            File inputFile = new File(localDir);
            if (!inputFile.exists()) // 如果文件夹不存在，则新建文件夹
            {
                inputFile.mkdirs();
            }
            SCPClient scpClient = conn.createSCPClient();
            scpClient.get(romoteFileName, localDir);
            conn.close();
        } catch (IOException e) {
            e.printStackTrace();
            return "出现了IO错误!";
        }
        return msg;
    }



}
