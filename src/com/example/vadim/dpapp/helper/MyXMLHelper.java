package com.example.vadim.dpapp.helper;

import com.example.vadim.dpapp.container.CompliteTaskContainer;
import com.example.vadim.dpapp.container.ContractorContainer;
import com.example.vadim.dpapp.container.OTaskContainer;
import org.w3c.dom.*;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.TransformerFactoryConfigurationError;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.*;
import java.util.ArrayList;

/**
 * Created by Vadim on 19.04.2017.
 */
public class MyXMLHelper {

    public void RemoveAllTask(String path){
        File file = new File(path);
        try {
            FileOutputStream fos = new FileOutputStream(file,false);
            OutputStreamWriter streamWriter = new OutputStreamWriter(fos,"UTF-8");
            //streamWriter.w
            String emptyXML = "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"no\"?><Задачи></Задачи>";
            //byte[] emptyXMLBytes = emptyXML.getBytes("UTF-8");
            streamWriter.write(emptyXML);
            streamWriter.close();
            fos.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void addDocument(String path,String codeDoc,String nameDoc,String message,String date) {
        DocumentBuilder documentBuilder = null;
        try {
            documentBuilder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        }
        Document doc = null;
        try {
            doc = documentBuilder.parse(path);
        } catch (SAXException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        Node root = doc.getDocumentElement();
        NodeList rootList = root.getChildNodes();
        org.w3c.dom.Element tagDoc = doc.createElement("Сообщение");
        tagDoc.setAttribute("Код", codeDoc);
        tagDoc.setAttribute("Наименование", nameDoc);
        tagDoc.setAttribute("ТекстСообщения", message);
        tagDoc.setAttribute("ПДата", date);
        int repeatingNode=0;
        for (int i = 0; i < rootList.getLength(); i++) {
            Node task = rootList.item(i);
            if (task.getNodeType() != Node.TEXT_NODE) {
                NamedNodeMap atts = task.getAttributes();
                Node att1 = atts.getNamedItem("Код");
                Node att2 = atts.getNamedItem("Наименование");
                Node att3 = atts.getNamedItem("ТекстСообщения");
                Node att4 = atts.getNamedItem("ПДата");
                if(tagDoc.getAttribute("Код").equals(att1.getTextContent())){
                    att2.setNodeValue(tagDoc.getAttribute("Наименование"));
                    att3.setTextContent(tagDoc.getAttribute("ТекстСообщения"));
                    att4.setTextContent(tagDoc.getAttribute("ПДата"));
                    repeatingNode++;
                }
            }
        }
        if(repeatingNode==0) {
            root.appendChild(tagDoc);
        }
        doc.normalize();
        writeXML(doc,AppConstant.xmlPathDoc);
    }

    public void addTask(String path, String codeTask, String nameTask, String contractorTask, String executor, String dateTask, String lastDate, ArrayList<OTaskContainer> discriptionTaskList, ArrayList<CompliteTaskContainer> compliteTaskList, String complite) {
        DocumentBuilder documentBuilder = null;
        try {
            documentBuilder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        }
        Document doc = null;
        try {
            doc = documentBuilder.parse(path);
        } catch (SAXException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        // remove the specific node
        int repeatingNode=0;
        Node root = doc.getDocumentElement();
        NodeList rootList = root.getChildNodes();
        org.w3c.dom.Element tagDoc = doc.createElement("Задача");
        tagDoc.setAttribute("Код", codeTask);
        tagDoc.setAttribute("Наименование", nameTask);
        tagDoc.setAttribute("Контрагент", contractorTask);
        tagDoc.setAttribute("Дата", dateTask);
        tagDoc.setAttribute("Исполнитель", executor);
        tagDoc.setAttribute("Выполнена", complite);
        tagDoc.setAttribute("ПДата",lastDate);
        for(OTaskContainer discriptionTask:discriptionTaskList) {
            org.w3c.dom.Element compliteWork = doc.createElement("ВыполненнаяРабота");
            compliteWork.setAttribute("КодАктива", discriptionTask.getCodeActiv());
            compliteWork.setAttribute("ОписаниеЗадачи", discriptionTask.getOpisanie());
            tagDoc.appendChild(compliteWork);
        }
        for(CompliteTaskContainer compliteTaskItem:compliteTaskList) {
            org.w3c.dom.Element compliteTask = doc.createElement("ОписаниеВыполненыхРабот");
            compliteTask.setAttribute("Ид", compliteTaskItem.getId());
            compliteTask.setAttribute("Дата", compliteTaskItem.getDate());
            compliteTask.setAttribute("ОписаниеРаботы", compliteTaskItem.getCompliteOTask());
            compliteTask.setAttribute("Время", compliteTaskItem.getTime());
            compliteTask.setAttribute("КодЗадачи", compliteTaskItem.getCodeTask());
            compliteTask.setAttribute("КодАктива", compliteTaskItem.getCodeActiv());
            tagDoc.appendChild(compliteTask);
        }

        /*for (int i = 0; i < rootList.getLength(); i++) {
            Node task = rootList.item(i);
            if (task.getNodeType() != Node.TEXT_NODE) {
                NamedNodeMap atts = task.getAttributes();
                Node att1 = atts.getNamedItem("Код");
                Node att2 = atts.getNamedItem("Наименование");
                Node att3 = atts.getNamedItem("Контрагент");
                Node att4 = atts.getNamedItem("Дата");
                Node att5 = atts.getNamedItem("Исполнитель");
                Node att6 = atts.getNamedItem("Выполнена");
                Node att7 = atts.getNamedItem("ПДата");
                NodeList attchild = task.getChildNodes();
                /*for(NodeList list:attchild){
                    Node attCode = attchild.item(1);
                    Node attName = attchild.item(2);
                }*/
                /*if(tagDoc.getAttribute("Код").equals(att1.getTextContent())){
                    att2.setNodeValue(tagDoc.getAttribute("Наименование"));
                    att3.setTextContent(tagDoc.getAttribute("Контрагент"));
                    att4.setTextContent(tagDoc.getAttribute("Дата"));
                    att5.setNodeValue(tagDoc.getAttribute("Исполнитель"));
                    att6.setTextContent(tagDoc.getAttribute("Выполнена"));
                    att7.setTextContent(tagDoc.getAttribute("ПДата"));
                    //for(int k=0;k<attchild.getLength();k++){
                   //     root.removeChild(attchild.item(k));
                  //  }
                   // root.appendChild(tagDoc);
                    repeatingNode++;
                }
            }
        }
        if(repeatingNode==0) {
            root.appendChild(tagDoc);
        }*/
        root.appendChild(tagDoc);
        doc.normalize();
        writeXML(doc,AppConstant.xmlPathZadachi);
    }

    public void addActiv(String path,String codeActiv,String nameActiv,String contractorActiv,String typeActiv,String shtrihActiv, String photo, String dateActiv, String codeContractor) {
        DocumentBuilder documentBuilder = null;
        try {
            documentBuilder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        }
        Document doc = null;
        try {
            doc = documentBuilder.parse(path);
        } catch (SAXException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        int repeatingNode=0;
        Node root = doc.getDocumentElement();
        NodeList rootList = root.getChildNodes();
        org.w3c.dom.Element tagDoc = doc.createElement("Актив");
        tagDoc.setAttribute("Код", codeActiv);
        tagDoc.setAttribute("Наименование", nameActiv);
        tagDoc.setAttribute("Контрагент", contractorActiv);
        tagDoc.setAttribute("ТипАктива", typeActiv);
        tagDoc.setAttribute("ШтрихКод", shtrihActiv);
       // tagDoc.setAttribute("Фото", photo);
        tagDoc.setAttribute("ПДата", dateActiv);
        tagDoc.setAttribute("КодКонтрагента", codeContractor);
        for (int i = 0; i < rootList.getLength(); i++) {
            Node task = rootList.item(i);
            if (task.getNodeType() != Node.TEXT_NODE) {
                NamedNodeMap atts = task.getAttributes();
                Node att1 = atts.getNamedItem("Код");
                Node att2 = atts.getNamedItem("Контрагент");
                Node att3 = atts.getNamedItem("Наименование");
                Node att4 = atts.getNamedItem("ТипАктива");
                Node att5 = atts.getNamedItem("ШтрихКод");
                //Node att6 = atts.getNamedItem("Фото");
                Node att7 = atts.getNamedItem("ПДата");
                Node att8 = atts.getNamedItem("КодКонтрагента");
                if(tagDoc.getAttribute("Код").equals(att1.getTextContent())){
                    att2.setNodeValue(tagDoc.getAttribute("Контрагент"));
                    att3.setTextContent(tagDoc.getAttribute("Наименование"));
                    att4.setTextContent(tagDoc.getAttribute("ТипАктива"));
                    att5.setTextContent(tagDoc.getAttribute("ШтрихКод"));
                    //att6.setTextContent(tagDoc.getAttribute("Фото"));
                    att7.setTextContent(tagDoc.getAttribute("ПДата"));
                    att8.setTextContent(tagDoc.getAttribute("КодКонтрагента"));
                    repeatingNode++;
                }
            }
        }
        if(repeatingNode==0) {
            root.appendChild(tagDoc);
        }
        doc.normalize();
        writeXML(doc,AppConstant.xmlPathActivi);
    }

    public void addRequestUser(String path,String login,String loginMobile,String contractor,String codeContractor, String uid,String post,String lastDate) {
        DocumentBuilder documentBuilder = null;
        try {
            documentBuilder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        }
        Document doc = null;
        try {
            doc = documentBuilder.parse(path);
        } catch (SAXException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        int repeatingNode=0;
        Node root = doc.getDocumentElement();
        NodeList rootList = root.getChildNodes();
        org.w3c.dom.Element tagDoc = doc.createElement("Пользователь");
        tagDoc.setAttribute("УИД", uid);
        tagDoc.setAttribute("Наименование", login);
        tagDoc.setAttribute("НомерТелефона", loginMobile);
        tagDoc.setAttribute("Контрагент", contractor);
        tagDoc.setAttribute("КодКонтрагента", codeContractor);
        tagDoc.setAttribute("Подтвержден", post);
        tagDoc.setAttribute("ПДата", lastDate);
        tagDoc.setAttribute("Права", "");
        for (int i = 0; i < rootList.getLength(); i++) {
            Node task = rootList.item(i);
            if (task.getNodeType() != Node.TEXT_NODE) {
                NamedNodeMap atts = task.getAttributes();
                Node att1 = atts.getNamedItem("УИД");
                Node att2 = atts.getNamedItem("Наименование");
                Node att3 = atts.getNamedItem("НомерТелефона");
                Node att4 = atts.getNamedItem("Контрагент");
                Node att5 = atts.getNamedItem("Подтвержден");
                Node att6 = atts.getNamedItem("ПДата");
                Node att7 = atts.getNamedItem("Права");
                Node att8 = atts.getNamedItem("КодКонтрагента");
                if(tagDoc.getAttribute("УИД").equals(att1.getTextContent())){
                    att2.setNodeValue(tagDoc.getAttribute("Наименование"));
                    att3.setTextContent(tagDoc.getAttribute("НомерТелефона"));
                    att4.setTextContent(tagDoc.getAttribute("Контрагент"));
                    att5.setTextContent(tagDoc.getAttribute("Подтвержден"));
                    att6.setTextContent(tagDoc.getAttribute("ПДата"));
                    att7.setTextContent(tagDoc.getAttribute("Права"));
                    att8.setTextContent(tagDoc.getAttribute("КодКонтрагента"));
                    repeatingNode++;
                }
            }
        }
        if(repeatingNode==0) {
            root.appendChild(tagDoc);
        }
        doc.normalize();
        writeXML(doc,AppConstant.xmlPathUser);
    }

    public ArrayList LoadContragent() {
        //listContractor.clear();
        ArrayList listContractor = new ArrayList<ContractorContainer>();
        DocumentBuilder documentBuilder = null;
        try {
            documentBuilder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        }
        Document doc = null;
        try {
            try {
                doc = documentBuilder.parse(AppConstant.xmlPathContractor);
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
                //sqlHelper.addContragent(att1.getTextContent());
                listContractor.add(new ContractorContainer(att1.getTextContent(),att2.getTextContent()));
            }
        }
        return listContractor;
    }

    private static void writeXML(Document document, String path) throws TransformerFactoryConfigurationError {
        try {
            Transformer tr = TransformerFactory.newInstance().newTransformer();
            tr.setOutputProperty(javax.xml.transform.OutputKeys.INDENT, "yes");
            DOMSource source = new DOMSource(document);
            FileOutputStream fos = new FileOutputStream(path);
            StreamResult result = new StreamResult(fos);
            tr.transform(source, result);
            fos.close();
        } catch (TransformerException | IOException e) {
            e.printStackTrace(System.out);
        }
    }
}
