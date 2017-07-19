package com.example.vadim.dpapp.helper;

import com.example.vadim.dpapp.container.*;
import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.bind.DatatypeConverter;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.TimerTask;

/**
 * Created by Vadim on 21.04.2017.
 */
public class TimerXML extends TimerTask {

    Connection con = null;
    String docString = "";
    MySQLHelper sqlHelper;
    MyXMLHelper helper;
    ArrayList<DocContainer> listDoc;
    ArrayList<ActivContainer> listActiv;
    ArrayList<TaskContainer> listTask;
    ArrayList<UserContainer> listUser;
    ArrayList<RegistrContainer> listRegistr;
    ArrayList<String> listContractor;

    public TimerXML() {
    }

    @Override
    public void run() {
        Runtime runtime = Runtime.getRuntime();
        sqlHelper = new MySQLHelper();
        helper = new MyXMLHelper();
        try {
            getUserWith1C();
            RefreshActiv();
            RefreshTask();
            RefreshDocuments();
            RefreshRegistr();
        }
        catch (Exception e){
            System.out.println("Error: " + e.toString());
            getUserWith1C();
            RefreshActiv();
            RefreshTask();
            RefreshDocuments();
            RefreshRegistr();
        }
            double memoryTotal = runtime.totalMemory()/(1024);
            double memoryMax = runtime.maxMemory()/(1024);
            System.out.println("Memory: " + memoryTotal/1024 + "/"+memoryMax/1024+  " Mb");
            System.gc();
        /*try {
            sqlHelper.con.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }*/

    }

    public void RefreshDocuments() {
        docString = "";
        //listDoc.clear();
        listDoc = new ArrayList<>();
        DocumentBuilder documentBuilder = null;
        try {
            documentBuilder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        }
        Document doc = null;
        try {
            try {
                doc = documentBuilder.parse(AppConstant.xmlPathDoc);
            } catch (IOException e) {
                e.printStackTrace();
            }
        } catch (SAXException e) {
            e.printStackTrace();
        }
        Node root = doc.getDocumentElement();
        NodeList rootList = root.getChildNodes();
        for (int i = 0; i < rootList.getLength(); i++) {
            Node document = rootList.item(i);
            if (document.getNodeType() != Node.TEXT_NODE) {
                NamedNodeMap atts = document.getAttributes();
                Node att1 = atts.getNamedItem("Код");
                Node att2 = atts.getNamedItem("Наименование");
                Node att3 = atts.getNamedItem("ТекстСообщения");
                Node att4 = atts.getNamedItem("ПДата");
                listDoc.add(new DocContainer(att1.getTextContent(), att2.getTextContent(), att3.getTextContent(), att4.getTextContent()));
            }
        }


        ArrayList<DocContainer> list = sqlHelper.getDocuments();
        Collections.sort(listDoc,DocContainer.compareByNumber);
        Collections.sort(list,DocContainer.compareByNumber);
        boolean flag = false;
        if (list.equals(listDoc)) {
            System.out.println("Сообщения идентичны");
        } else {
            System.out.println("Новое ообщение");
            java.util.Date xmlDate = null;
            java.util.Date dbDate = null;
            List<DocContainer> listGreat;
            List<DocContainer> listLow;
            boolean flagSQL = false;
            if (listDoc.size() >= list.size()) {
                listGreat = listDoc;
                listLow = list;
                flagSQL = false;
            } else {
                listGreat = list;
                listLow = listDoc;
                flagSQL = true;
            }
            for (DocContainer xml : listGreat) {
                flag = false;
                if (listLow.size() > 0) {
                    for (DocContainer db : listLow) {
                        if (xml.getCodeDoc().equals(db.getCodeDoc())) {
                            flag = true;
                            try {
                                if (flagSQL) {
                                    String date = xml.getDateDoc().substring(0, 19);
                                    xmlDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(date);
                                    dbDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(db.getDateDoc());
                                } else {
                                    String date = db.getDateDoc().substring(0, 19);
                                    xmlDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(xml.getDateDoc());
                                    dbDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(date);
                                }

                            } catch (ParseException e) {
                                e.printStackTrace();
                            }
                            if (xmlDate.getTime() > dbDate.getTime()) {
                                sqlHelper.addDocument(xml.getCodeDoc(), xml.getAvtorDoc(), xml.getMessageDoc());
                            } else {
                                sqlHelper.addDocument(db.getCodeDoc(), db.getAvtorDoc(), db.getMessageDoc());
                            }
                            break;
                        }
                    }
                    if (!flag) {
                        sqlHelper.addDocument(xml.getCodeDoc(), xml.getAvtorDoc(), xml.getMessageDoc());
                    }
                } else {
                    sqlHelper.addDocument(xml.getCodeDoc(), xml.getAvtorDoc(), xml.getMessageDoc());
                }
            }
            listGreat = sqlHelper.getDocuments();

            for (DocContainer container : listGreat) {
                helper.addDocument(AppConstant.xmlPathDoc, container.getCodeDoc(), container.getAvtorDoc(), container.getMessageDoc(), container.getDateDoc());
            }
        }
        listDoc=null;
        list = null;
    }

    public void RefreshActiv() {
        String activString = "";
        try {
            //listActiv.clear();
            listActiv = new ArrayList<>();
            DocumentBuilder documentBuilder = null;
            try {
                documentBuilder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
            } catch (ParserConfigurationException e) {
                e.printStackTrace();
            }
            Document doc = null;
            try {
                doc = documentBuilder.parse(AppConstant.xmlPathActivi);
            } catch (SAXException e) {
                e.printStackTrace();
            }
            Node root = doc.getDocumentElement();
            NodeList rootList = root.getChildNodes();
            for (int i = 0; i < rootList.getLength(); i++) {
                Node document = rootList.item(i);
                if (document.getNodeType() != Node.TEXT_NODE) {
                    NamedNodeMap atts = document.getAttributes();
                    Node att1 = atts.getNamedItem("Код");
                    Node att2 = atts.getNamedItem("Наименование");
                    Node att3 = atts.getNamedItem("Контрагент");
                    Node att5 = atts.getNamedItem("ТипАктива");
                    Node att6 = atts.getNamedItem("ШтрихКод");
                    Node att7 = atts.getNamedItem("Фото");
                    Node att4 = atts.getNamedItem("ПДата");
                    Node att8 = atts.getNamedItem("КодКонтрагента");
                    activString = activString + att1 + att2 + att3 + att4 + att5 + att6 + att7 + ";";
                    listActiv.add(new ActivContainer(String.valueOf(Integer.parseInt(att1.getTextContent())), att2.getTextContent(), att3.getTextContent(), att5.getTextContent(),
                            att6.getTextContent(), ""/*att7.getTextContent()*/, att4.getTextContent(),att8.getTextContent()));
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        ArrayList<ActivContainer> list = sqlHelper.getActivs("");
        Collections.sort(listActiv, ActivContainer.compareByNumber);
        boolean flag = false;
        if (list.equals(listActiv)) {
            System.out.println("Активы идентичны");
        } else {
            System.out.println("Новый актив");
            java.util.Date xmlDate = null;
            java.util.Date dbDate = null;
            List<ActivContainer> listGreat;
            List<ActivContainer> listLow;
            boolean flagSQL = false;
            if (listActiv.size() >= list.size()) {
                listGreat = listActiv;
                listLow = list;
                flagSQL = false;
            } else {
                listGreat = list;
                listLow = listActiv;
                flagSQL = true;
            }
            for (ActivContainer xml : listGreat) {
                flag = false;
                if (listLow.size() > 0) {
                    for (ActivContainer db : listLow) {
                        if (xml.getCodeActiv().equals(db.getCodeActiv())) {
                            flag = true;
                            try {
                                if (flagSQL) {
                                    String date = xml.getDate().substring(0, 19);
                                    xmlDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(date);
                                    dbDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(db.getDate());
                                } else {
                                    String date = db.getDate().substring(0, 19);
                                    xmlDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(xml.getDate());
                                    dbDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(date);
                                }

                            } catch (ParseException e) {
                                e.printStackTrace();
                            }
                            if (xmlDate.getTime() > dbDate.getTime()) {
                                sqlHelper.addActiv(xml.getCodeActiv(), xml.getNameActiv(), xml.getShtrihActiv(), xml.getTypeActiv(), "", xml.getContractorActiv(),xml.getCodeContractor());
                            } else {
                                sqlHelper.addActiv(db.getCodeActiv(), db.getNameActiv(), db.getShtrihActiv(), db.getTypeActiv(),"", db.getContractorActiv(),db.getCodeContractor());
                            }
                            break;
                        }
                    }
                    if (!flag) {
                        sqlHelper.addActiv(xml.getCodeActiv(), xml.getNameActiv(), xml.getShtrihActiv(), xml.getTypeActiv(), "", xml.getContractorActiv(),xml.getCodeContractor());
                    }
                } else {
                    sqlHelper.addActiv(xml.getCodeActiv(), xml.getNameActiv(), xml.getShtrihActiv(), xml.getTypeActiv(), "", xml.getContractorActiv(),xml.getCodeContractor());
                }
            }
            listGreat = sqlHelper.getActivs("");

            for (ActivContainer container : listGreat) {
                helper.addActiv(AppConstant.xmlPathActivi, addNoliki(container.getCodeActiv()), container.getNameActiv(), container.getContractorActiv(),
                        container.getTypeActiv(), container.getShtrihActiv(), container.getPhoto(), container.getDate(),container.getCodeContractor());
            }
        }
        listActiv = null;
        list = null;
    }

    public String addNoliki(String code) {
        String newCode = code;
        for (int i = 0; i < 9 - code.length(); i++)
            newCode = "0" + newCode;
        return newCode;
    }

    public void RefreshTask() {
        ArrayList<OTaskContainer> arrayOTasks = new ArrayList();
        ArrayList<CompliteTaskContainer> arrayCompliteTask = new ArrayList<>();
        try {
            //listTask.clear();
            listTask = new ArrayList<>();
            DocumentBuilder documentBuilder = null;
            try {
                documentBuilder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
            } catch (ParserConfigurationException e) {
                e.printStackTrace();
            }
            Document doc = null;
            try {
                doc = documentBuilder.parse(AppConstant.xmlPathZadachi);
            } catch (SAXException e) {
                e.printStackTrace();
            }
            Node root = doc.getDocumentElement();
            NodeList rootList = root.getChildNodes();
            for (int i = 0; i < rootList.getLength(); i++) {
                //arrayOTasks.clear();
               // arrayCompliteTask.clear();
                arrayOTasks = new ArrayList<>();
                arrayCompliteTask = new ArrayList<>();

                Node document = rootList.item(i);
                if (document.getNodeType() != Node.TEXT_NODE) {
                    NamedNodeMap atts = document.getAttributes();
                    Node att1 = atts.getNamedItem("Код");
                    Node att2 = atts.getNamedItem("Наименование");
                    Node att3 = atts.getNamedItem("Контрагент");
                    Node att4 = atts.getNamedItem("Дата");
                    Node att5 = atts.getNamedItem("Исполнитель");
                    Node att6 = atts.getNamedItem("Выполнена");
                    Node att7 = atts.getNamedItem("ПДата");
                    if(att6.getNodeValue().equals("Нет")) {
                        NodeList otasks = document.getChildNodes();
                        for (int j = 0; j < otasks.getLength(); j++) {
                            Node otask = otasks.item(j);
                            if (otask.getNodeType() != Node.TEXT_NODE) {
                                if(otask.getNodeName().equals("ВыполненнаяРабота")) {
                                    NamedNodeMap oatts = otask.getAttributes();
                                    Node oatt1 = oatts.getNamedItem("КодАктива");
                                    Node oatt2 = oatts.getNamedItem("ОписаниеЗадачи");
                                    arrayOTasks.add(new OTaskContainer("", att1.getNodeValue(), oatt2.getNodeValue(), oatt1.getNodeValue()));
                                }
                                else if(otask.getNodeName().equals("ОписаниеВыполненыхРабот")) {
                                    NamedNodeMap oatts = otask.getAttributes();
                                    Node catt1 = oatts.getNamedItem("Ид");
                                    Node catt2 = oatts.getNamedItem("Дата");
                                    Node catt3 = oatts.getNamedItem("ОписаниеРаботы");
                                    Node catt4 = oatts.getNamedItem("Время");
                                    Node catt5 = oatts.getNamedItem("КодЗадачи");
                                    Node catt6 = oatts.getNamedItem("КодАктива");
                                    arrayCompliteTask.add(new CompliteTaskContainer("", catt2.getNodeValue(), catt3.getNodeValue(), catt4.getNodeValue(),catt5.getNodeValue(),catt6.getNodeValue()));
                                }
                            }
                        }
                        listTask.add(new TaskContainer(att1.getNodeValue(), att2.getNodeValue(), att3.getNodeValue(),
                                att4.getNodeValue(), att5.getNodeValue(), att6.getNodeValue(), att7.getNodeValue(), new ArrayList(arrayOTasks),new ArrayList(arrayCompliteTask)));
                    }
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        ArrayList<TaskContainer> list = sqlHelper.getTask(false, null);
        Collections.sort(list,TaskContainer.compareByNumber );
        Collections.sort(listTask,TaskContainer.compareByNumber);
        //System.out.println(list);
        //System.out.println(listTask);
        boolean flag = false;
        if (list.equals(listTask)) {
            System.out.println("Поручения идентичны");
        } else {
            System.out.println("Новое Поручение");
            java.util.Date xmlDate = null;
            java.util.Date dbDate = null;
            List<TaskContainer> listGreat;
            List<TaskContainer> listLow;
            boolean flagSQL = false;
            //if ((listTask.size() >= list.size())||(listTask.toString().length()>=list.toString().length())) {
            if (listTask.toString().length()>=list.toString().length()) {
                listGreat = listTask;
                listLow = list;
                flagSQL = false;
            } else {
                listGreat = list;
                listLow = listTask;
                flagSQL = true;
            }

            for (TaskContainer xml : listGreat) {
                flag = false;
                if (listLow.size() > 0) {
                    for (TaskContainer db : listLow) {
                        if (xml.getCode().equals(db.getCode())) {
                            flag = true;
                            try {
                                if (flagSQL) {
                                    String date = xml.getLastDate().substring(0, 19);
                                    xmlDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(date);
                                    dbDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(db.getLastDate());
                                } else {
                                    String date = db.getLastDate().substring(0, 19);
                                    xmlDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(xml.getLastDate());
                                    dbDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(date);
                                }

                            } catch (ParseException e) {
                                e.printStackTrace();
                            }
                            if (xmlDate.getTime() > dbDate.getTime()) {
                                sqlHelper.addTask(xml.getCode(),xml.getTaskName(),xml.getContractor(),xml.getDate(),xml.getExecutor(),xml.getComplite(),xml.getLastDate());
                                if(xml.getOtasks().size()>0) {
                                    sqlHelper.addDescriptionTask(xml.getOtasks());
                                }
                                if(xml.getCompliteTasks().size()>0) {
                                    sqlHelper.addCompliteTask(xml.getCompliteTasks());
                                }
                            } else {
                                sqlHelper.addTask(db.getCode(),db.getTaskName(),db.getContractor(),db.getDate(),db.getExecutor(),db.getComplite(),db.getLastDate());
                                if(db.getOtasks().size()>0){
                                    sqlHelper.addDescriptionTask(db.getOtasks());
                                }
                                if(db.getCompliteTasks().size()>0){
                                    sqlHelper.addCompliteTask(db.getCompliteTasks());
                                }
                            }
                            break;
                        }
                    }
                    if (!flag) {
                        sqlHelper.addTask(xml.getCode(),xml.getTaskName(),xml.getContractor(),xml.getDate(),xml.getExecutor(),xml.getComplite(),xml.getLastDate());
                        if(xml.getOtasks().size()>0)
                            sqlHelper.addDescriptionTask(xml.getOtasks());
                        if(xml.getCompliteTasks().size()>0)
                            sqlHelper.addCompliteTask(xml.getCompliteTasks());
                    }
                } else {
                    sqlHelper.addTask(xml.getCode(),xml.getTaskName(),xml.getContractor(),xml.getDate(),xml.getExecutor(),xml.getComplite(),xml.getLastDate());
                    if(xml.getOtasks().size()>0)
                        sqlHelper.addDescriptionTask(xml.getOtasks());
                    if(xml.getCompliteTasks().size()>0)
                        sqlHelper.addCompliteTask(xml.getCompliteTasks());
                }
            }
            listGreat = sqlHelper.getTask(false,null);
            helper.RemoveAllTask(AppConstant.xmlPathZadachi);
            for (TaskContainer container : listGreat) {
                helper.addTask(AppConstant.xmlPathZadachi,container.getCode(),container.getTaskName(),container.getContractor(),container.getExecutor(), container.getDate(),
                        container.getLastDate(), sqlHelper.getDescriptionTask(container.getCode(),false),sqlHelper.getCompliteTask(container.getCode(),false), container.getComplite());
            }
            sqlHelper.deleteCompliteTask();
        }
        list = null;
        listTask = null;
    }

    public void getUserWith1C() {
        System.out.println("Загрузка пользователей");
        listUser = new ArrayList<>();
        DocumentBuilder documentBuilder = null;
        try {
            documentBuilder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        }
        Document doc = null;
        try {
            try {
                doc = documentBuilder.parse(AppConstant.xmlPathUser);
            } catch (IOException e) {
                e.printStackTrace();
            }
        } catch (SAXException e) {
            e.printStackTrace();
        }
        Node root = doc.getDocumentElement();
        NodeList rootList = root.getChildNodes();
        for (int i = 0; i < rootList.getLength(); i++) {
            Node document = rootList.item(i);
            if (document.getNodeType() != Node.TEXT_NODE) {
                NamedNodeMap atts = document.getAttributes();
                Node att1 = atts.getNamedItem("Наименование");
                Node att2 = atts.getNamedItem("Контрагент");
                Node att3 = atts.getNamedItem("НомерТелефона");
                Node att4 = atts.getNamedItem("УИД");
                Node att5 = atts.getNamedItem("Права");
                Node att6 = atts.getNamedItem("Подтвержден");
                Node att7 = atts.getNamedItem("ПДата");
                Node att8 = atts.getNamedItem("КодКонтрагента");
                sqlHelper.addUser(att1.getNodeValue(), att6.getNodeValue(), att4.getNodeValue(), att2.getNodeValue(), att3.getNodeValue(), att5.getNodeValue(),att8.getNodeValue());
            }
        }
        listUser = null;
    }

    public void RefreshRegistr() {
        try {
            listRegistr = new ArrayList<>();
            DocumentBuilder documentBuilder = null;
            try {
                documentBuilder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
            } catch (ParserConfigurationException e) {
                e.printStackTrace();
            }
            Document doc = null;
            try {
                doc = documentBuilder.parse(AppConstant.xmlPathOtchet);
            } catch (SAXException e) {
                e.printStackTrace();
            }
            Node root = doc.getDocumentElement();
            NodeList rootList = root.getChildNodes();
            for (int i = 0; i < rootList.getLength(); i++) {
                Node document = rootList.item(i);
                if (document.getNodeType() != Node.TEXT_NODE) {
                    NamedNodeMap atts = document.getAttributes();
                    Node att1 = atts.getNamedItem("Код");
                    Node att2 = atts.getNamedItem("Организация");
                    Node att3 = atts.getNamedItem("ПодразделениеОрганизации");
                    Node att4 = atts.getNamedItem("МОЛ");
                    Node att5 = atts.getNamedItem("Контрагент");
                    Node att6 = atts.getNamedItem("ПодразделениеКонтрагента");
                    Node att7 = atts.getNamedItem("МОЛКонтрагента");
                    Node att8 = atts.getNamedItem("ПДата");
                    Node att9 = atts.getNamedItem("ШтрихКод");
                    Node att10 = atts.getNamedItem("НаименованиеАктива");
                    Node att11 = atts.getNamedItem("Состояние");
                listRegistr.add(new RegistrContainer(att1.getTextContent(), att2.getTextContent(), att3.getTextContent(), att4.getTextContent()
                            , att5.getTextContent(), att6.getTextContent(), att7.getTextContent(), att8.getTextContent(), att9.getTextContent(),att10.getTextContent(), att11.getTextContent()));
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        ArrayList<RegistrContainer> list = sqlHelper.getReport("");

        if (list.equals(listRegistr)) {
            System.out.println("Отчёты идентичны");
        } else {
            System.out.println("Новый отчёт");
            java.util.Date xmlDate = null;
            java.util.Date dbDate = null;
            if (listRegistr.size() >= list.size()) {
                for (RegistrContainer xml : listRegistr) {
                    if (list.size() > 0) {
                        for (RegistrContainer db : list) {
                            if (xml.getCode().equals(db.getCode())) {

                                try {
                                    xmlDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(xml.getLastDate());
                                    dbDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(db.getLastDate());
                                    //dbDate.toGMTString();
                                } catch (ParseException e) {
                                    e.printStackTrace();
                                }

                                if (xmlDate.getTime() > dbDate.getTime()) {
                                    sqlHelper.addReport(xml.getCode(), xml.getOrganization(), xml.getDivisionOfOrganizations(),
                                            xml.getMol(), xml.getContractor(), xml.getDivisionOfContractor(), xml.getMolContractor(), xml.getLastDate(), xml.getShtrihCode(),xml.getNameActiv(),xml.getStatus());
                                }

                            } else {
                                sqlHelper.addReport(xml.getCode(), xml.getOrganization(), xml.getDivisionOfOrganizations(),
                                        xml.getMol(), xml.getContractor(), xml.getDivisionOfContractor(), xml.getMolContractor(), xml.getLastDate(), xml.getShtrihCode(),xml.getNameActiv(),xml.getStatus());
                                //sqlHelper.addDocument(xml.getCodeDoc(), URLDecoder.decode(xml.getAvtorDoc()), URLDecoder.decode(xml.getMessageDoc()));
                            }
                        }
                    } else {
                        sqlHelper.addReport(xml.getCode(), xml.getOrganization(), xml.getDivisionOfOrganizations(),
                                xml.getMol(), xml.getContractor(), xml.getDivisionOfContractor(), xml.getMolContractor(), xml.getLastDate(), xml.getShtrihCode(),xml.getNameActiv(),xml.getStatus());
                    }
                }
            }
        }
        listRegistr = null;
        list = null;
    }


}

