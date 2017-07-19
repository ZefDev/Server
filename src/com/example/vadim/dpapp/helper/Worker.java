package com.example.vadim.dpapp.helper;

import com.example.vadim.dpapp.container.*;
import org.json.JSONArray;

import java.io.*;
import java.net.Socket;
import java.net.URLDecoder;
import java.util.ArrayList;

public class Worker implements Runnable{

    private Socket socket;
    private InputStream is;
    private OutputStream os;

    Converter converter;

    private ArrayList<TaskContainer> taskList;
    private ArrayList<DocContainer> docList;
    private ArrayList<ActivContainer> activList;
    private ArrayList<UserContainer> answer;
    private ArrayList<ContractorContainer> contractorList;
    private ArrayList<RegistrContainer> otchetList;
    private String imageActiv="";
    MySQLHelper helper;
    MyXMLHelper XMLhelper;
    String user;
    String contractor="";
    String shtrih="";
    String UIDUser="";

    public Worker(Socket socket) throws IOException {
        this.socket = socket;
        this.is = socket.getInputStream();
        this.os = socket.getOutputStream();
    }

    @Override
    public void run() {
        try {
            helper = new MySQLHelper();
            XMLhelper = new MyXMLHelper();
            String finalStr = readInputHeaders();
            String tag = finalStr.substring(finalStr.indexOf("tag=")+4, finalStr.indexOf("&",finalStr.indexOf("tag=")+4));
            user = finalStr.substring(finalStr.indexOf("user=")+5, finalStr.indexOf("&",finalStr.indexOf("user=")+5));
            JSONArray jsonarray = null;
            ArrayList str = null;
            System.out.println("Tag:  "+ tag);
            switch (tag){
                case "get_tasks":
                    readFile(AppConstant.xmlPathZadachi);
                    jsonarray = new JSONArray(taskList);
                    break;
                case "get_documents":
                    readFile(AppConstant.xmlPathDoc);
                    jsonarray = new JSONArray(docList);
                    break;
                case "get_activ":
                    contractor = finalStr.substring(finalStr.indexOf("contractor=")+11, finalStr.indexOf("&",finalStr.indexOf("contractor=")+11));
                    readFile(AppConstant.xmlPathActivi);
                    jsonarray = new JSONArray(activList);
                    break;
                case "get_activ_image":
                    shtrih = finalStr.substring(finalStr.indexOf("shtrih=")+7, finalStr.indexOf("&",finalStr.indexOf("shtrih=")+7));
                    readFile(AppConstant.xmlPathActivi);
                    str = new ArrayList<>();
                    if(!imageActiv.equals("")) {
                        str.add("true");
                        str.add(imageActiv);
                    }
                    else {
                        str.add("false");
                    }
                    jsonarray = new JSONArray(str);
                    break;
                case "get_activ_contractor":
                    contractor = finalStr.substring(finalStr.indexOf("contractor=")+11, finalStr.indexOf("&",finalStr.indexOf("contractor=")+11));
                    readFile(AppConstant.xmlPathOtchet);
                    jsonarray = new JSONArray(otchetList);
                    break;
                case "get_user":
                    UIDUser = finalStr.substring(finalStr.indexOf("uid=")+4, finalStr.indexOf("&",finalStr.indexOf("uid=")+4));
                    readFile(AppConstant.xmlPathUser);
                    jsonarray = new JSONArray(answer);
                    System.out.println(UIDUser);
                    break;
                case "get_contractor":
                    readFile(AppConstant.xmlPathContractor);
                    jsonarray = new JSONArray(contractorList);
                    break;
                case "send_documents":
                    writeDocuments(finalStr);
                    str = new ArrayList<>();
                    str.add("true");
                    jsonarray = new JSONArray(str);
                    break;
                case "send_task":
                    writeTask(finalStr);
                    break;
                case "send_activ":
                    writeActiv(finalStr);
                    ArrayList<String> strr = new ArrayList<String>();
                    strr.add("true");
                    jsonarray = new JSONArray(strr);
                    break;
                case "send_user":
                    writeUser(finalStr);
                    break;
            }
            writeResponse(jsonarray.toString());
        } catch (Throwable t) {
        } finally {
            try {
                socket.close();
            } catch (Throwable t) {
            }
        }
        System.err.println("Client processing finished");
    }

    private void writeResponse(String s) throws Throwable {
        byte[] body = s.getBytes();
        String response = "HTTP/1.1 200 OK\r\n" +
                "Server: YarServer/2009-09-09\r\n" +
                "Content-Type: text/html; charset=cp1251\r\n" +  //cp1251
                "Content-Length: " + body.length + "\r\n" +
                "Connection: close\r\n\r\n";
        os.write(response.getBytes());
        os.write(body);
        os.flush();

    }

    private String readInputHeaders() throws Throwable {
        BufferedReader br = new BufferedReader(new InputStreamReader(is));
        StringBuffer sb = new StringBuffer();
        String result = null;
        String getString = null;
        String postString = null;
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        byte[] data = null;
        String resultString = null;
        int size = 8192;
        int sizeCurr=0;
        int sizeMax = 0;
        byte[] buffer = new byte[8192]; // Такого вот размера буфер
        // Далее, например, вот так читаем ответ
        int bytesRead;
        boolean flagina = false;
        while((bytesRead = is.read(buffer))!=0){
            baos.write(buffer, 0, bytesRead);
            if(!flagina) {
                data = baos.toByteArray();
                String tmp = new String(data, "utf-8"); //windows-1251
                int contentLength = Integer.parseInt(tmp.substring(tmp.indexOf("Content-Length:")+16,tmp.indexOf("\r\n\r\n")));
                if(contentLength>7500)
                    sizeMax = contentLength+bytesRead;
                else
                    sizeMax = bytesRead;
                flagina = true;
            }
            sizeCurr+=bytesRead;
            if((sizeCurr==sizeMax))
                break;
        }
        data = baos.toByteArray();

        resultString = new String(data, "utf-8");
        String finalStr = null;
        finalStr = resultString.substring(resultString.indexOf("Content-Length:")+15);
        return URLDecoder.decode(finalStr,"utf-8");
    }

    private void readFile(String path) {
        switch (path) {
            case AppConstant.xmlPathZadachi:
                taskList = new ArrayList<>();
                taskList = helper.getTask(false, user);
                break;
            case AppConstant.xmlPathDoc:
                docList = new ArrayList<>();
                docList = helper.getDocuments();
                break;
            case AppConstant.xmlPathUser:
                answer = new ArrayList<UserContainer>();
                ArrayList<UserContainer> userList = new ArrayList<>();
                userList = helper.getUsers("get_user", null);
                int count = 0;
                for (UserContainer item : userList) {
                    if (item.getUID().equals(UIDUser) | item.getPost().equals("true")) {
                        count++;
                        answer.add(new UserContainer("", item.getLogin(), item.getPost(), "", item.getRight(), item.getContractor()));
                    }
                }
                if (count == 0) {
                    answer.add(new UserContainer("", "", "false", "", "", ""));
                }
                break;
            case AppConstant.xmlPathActivi:
                if (contractor.equals("")) {
                    if(shtrih.equals("")){
                        activList = new ArrayList<>();
                        activList = helper.getActivs("");
                    }
                    else{
                        imageActiv = helper.getImageByShtrih(shtrih);
                    }
                } else {
                    activList = helper.getActivs(contractor);
                }

                break;
            case AppConstant.xmlPathOtchet:
                otchetList = helper.getReport(contractor);
                break;
            case AppConstant.xmlPathContractor:
                contractorList = helper.getContractor();
                break;
        }


    }

    public void writeDocuments(String string){
        String message=string.substring(string.indexOf("message=") + 8, string.indexOf("&",string.indexOf("message=") + 8));
        String nameDoc=string.substring(string.indexOf("nameDoc=") + 8, string.indexOf("&",string.indexOf("nameDoc=") + 8));
        String codeDoc=string.substring(string.indexOf("codeDoc=") + 8, string.indexOf("&",string.indexOf("codeDoc=") + 8));
        helper.addDocument(codeDoc,nameDoc,message);
    }
    public void writeTask(String string){

        String stringCompliteTasks = string.substring(string.indexOf("listCompliteTask=[") + 18, string.indexOf("]",string.indexOf("listCompliteTask=[") + 18));
        string = string.replace(stringCompliteTasks,"");
        String codeTask=string.substring(string.indexOf("codeTask=") + 9, string.indexOf("&",string.indexOf("codeTask=") + 9));
        String contractorTask=string.substring(string.indexOf("contractorTask=") + 15, string.indexOf("&",string.indexOf("contractorTask=") + 15));
        String dateTask=string.substring(string.indexOf("dateTask=") + 9, string.indexOf("&",string.indexOf("dateTask=") + 9));
        String nameTask=string.substring(string.indexOf("nameTask=") + 9, string.indexOf("&",string.indexOf("nameTask=") + 9));
        String ispolnitel=string.substring(string.indexOf("ispolnitel=") + 11, string.indexOf("&",string.indexOf("ispolnitel=") + 11));
        String complite=string.substring(string.indexOf("complite=") + 9, string.indexOf("&",string.indexOf("complite=") + 9));
        helper.addTask(codeTask,nameTask,contractorTask,dateTask,ispolnitel,complite,null);
        writeCompliteTask(stringCompliteTasks);
    }

    public void writeActiv(String string){
        String codeActiv=string.substring(string.indexOf("codeActiv=") + 10, string.indexOf("&",string.indexOf("codeActiv=") + 10));
        String nameActiv=string.substring(string.indexOf("nameActiv=") + 10, string.indexOf("&",string.indexOf("nameActiv=") + 10));
        String typeActiv=string.substring(string.indexOf("typeActiv=") + 10, string.indexOf("&",string.indexOf("typeActiv=") + 10));
        String shtrihCode=string.substring(string.indexOf("shtrihCode=") + 11, string.indexOf("&",string.indexOf("shtrihCode=") + 11));
        String photo=string.substring(string.indexOf("photo=") + 6, string.indexOf("&",string.indexOf("photo=") + 6));
        String contractor=string.substring(string.indexOf("contractor=") + 11, string.indexOf("&",string.indexOf("contractor=") + 11));
        String codeContractor = helper.getContractor(contractor);
        helper.addActiv(codeActiv,nameActiv,shtrihCode,typeActiv,photo,contractor,codeContractor);
        converter = new Converter(photo,shtrihCode);
    }

    public boolean writeUser(String string){
        String login=string.substring(string.indexOf("login=") + 6, string.indexOf("&",string.indexOf("login=") + 6));
        String loginMobile=string.substring(string.indexOf("loginMobile=") + 12, string.indexOf("&",string.indexOf("loginMobile=") + 12));
        String contractor=string.substring(string.indexOf("contractor=") +11, string.indexOf("&",string.indexOf("contractor=") + 11));
        String uid=string.substring(string.indexOf("uid=") + 4, string.indexOf("&",string.indexOf("uid=") + 4));
        String post=string.substring(string.indexOf("post=") + 5, string.indexOf("&",string.indexOf("post=") + 5));
        ArrayList<UserContainer> list = helper.getUsers("get_user",null);
        int count=0;
        for(UserContainer u: list){
            if((u.getUID().equals(uid))){
                count++;
            }
        }
        if (count==0) {
            helper.addUser(login, post, uid, contractor, loginMobile,"","");
            ArrayList<UserContainer> dateUser = helper.getUsers("get_lastDate",uid);
            XMLhelper.addRequestUser(AppConstant.xmlPathUser,login,loginMobile,contractor,"",uid,post,dateUser.get(0).getLastDate());
            return true;
        }
        return false;
    }

    public void writeCompliteTask(String string){
        string = string.replace("listCompliteTask=[","");
        int count = 0;
        boolean flag = true;
        String testString = string;
        do{
            flag = true;
            if(testString.contains("CompliteTaskContainer{")){
                count++;
                testString =  testString.substring(testString.indexOf("CompliteTaskContainer{") + 22,testString.length());
            }
            else {
                flag = false;
            }
        }while(flag);
        ArrayList<CompliteTaskContainer> list = new ArrayList<CompliteTaskContainer>();
        for(int i=0;i<count;i++){
            String stringCompliteTask = string.substring(string.indexOf("CompliteTaskContainer{"), string.indexOf("}",string.indexOf("CompliteTaskContainer{")));
            string = string.replace(stringCompliteTask,"");
            String date=stringCompliteTask.substring(stringCompliteTask.indexOf("date=\'") + 6, stringCompliteTask.indexOf("\'",stringCompliteTask.indexOf("date=\'") + 6));
            String compliteOTask=stringCompliteTask.substring(stringCompliteTask.indexOf("compliteTask=\'") + 14, stringCompliteTask.indexOf("\'",stringCompliteTask.indexOf("compliteTask=\'") + 14));
            String time=stringCompliteTask.substring(stringCompliteTask.indexOf("time=\'") + 6, stringCompliteTask.indexOf("\'",stringCompliteTask.indexOf("time=\'") + 6));
            String codeTask=stringCompliteTask.substring(stringCompliteTask.indexOf("codeTask=\'") + 10, stringCompliteTask.indexOf("\'",stringCompliteTask.indexOf("codeTask=\'") + 10));
            String codeActiv=stringCompliteTask.substring(stringCompliteTask.indexOf("codeActiv=\'") + 11, stringCompliteTask.indexOf("\'",stringCompliteTask.indexOf("codeActiv=\'") + 11));
            list.add(new CompliteTaskContainer("",date,compliteOTask,time,codeTask,codeActiv));
        }
        helper.addCompliteTask(list);
    }
}
