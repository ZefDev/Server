package com.example.vadim.dpapp.helper;

import com.example.vadim.dpapp.container.*;

import java.sql.*;
import java.util.ArrayList;

public class MySQLHelper {
    Connection con;
    Statement statement;
    ResultSet resultSet;

    public MySQLHelper() {
        this.con = getConnection();
        this.statement = null;
        this.resultSet = null;
    }

    public Connection getConnection(){
        Connection connection = null;
        ResultSet resultSet ;try {
            connection = DriverManager.getConnection(AppConstant.url, AppConstant.user, AppConstant.password);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return connection;
    }

    public void addTask(String codeTask,String nameTask,String contractorTask,String dateTask,String ispolnitel,String complite, String lastDate){
        ResultSet resultSet ;
        try {
            //con = getConnection();
            statement =  con.createStatement();
            resultSet = statement.executeQuery("SELECT * FROM task WHERE Code='" +codeTask+ "'");
            resultSet.last();
            int rowCount = resultSet.getRow();
            if(rowCount>0){
                if(lastDate==null){
                    statement.execute("UPDATE task SET Name = '" + nameTask + "', " +
                            "Kontracter = '" + contractorTask + "', Date = '" + dateTask + "', Ispolnitel = '"
                            + ispolnitel + "', Complite = '" + complite + "', lastDate = NOW() WHERE Code = '" + codeTask + "'");
                }
                else{
                    statement.execute("UPDATE task SET Name = '" + nameTask + "', " +
                            "Kontracter = '" + contractorTask + "', Date = '" + dateTask + "', Ispolnitel = '"
                            + ispolnitel + "', Complite = '" + complite + "', lastDate = '" + lastDate + "' WHERE Code = '" + codeTask + "'");
                }
            }
            else {
                if(lastDate==null)
                    statement.execute("INSERT INTO task(Code, Name, Kontracter, Date, Ispolnitel, Complite, lastDate) " +
                        "values('" + codeTask + "', '" + nameTask + "', '" + contractorTask + "', '" + dateTask + "', '" + ispolnitel + "', '" + complite + "',NOW())");
                else
                    statement.execute("INSERT INTO task(Code, Name, Kontracter, Date, Ispolnitel, Complite, lastDate) " +
                            "values('" + codeTask + "', '" + nameTask + "', '" + contractorTask + "', '" + dateTask + "', '" + ispolnitel + "', '" + complite + "','"+lastDate+"')");

            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        finally {
            //finaleClose();
        }
    }

    public void addDocument(String codeDoc, String nameDoc, String message) {

        //con = getConnection();
        ResultSet resultSet ;
        try {
            statement =  con.createStatement();

            resultSet = statement.executeQuery("SELECT * FROM documents WHERE codeDoc='" +codeDoc+ "'");
            resultSet.last();
            int rowCount = resultSet.getRow();
            if(rowCount>0){
                statement.execute( "UPDATE documents SET avtorDoc = '" + nameDoc + "', " +
                        "messageDoc = '" + message + "',"+"dateDoc = NOW() WHERE codeDoc = '" + codeDoc+"'");
            }
            else {
                statement.execute("INSERT INTO documents(codeDoc, avtorDoc, messageDoc, dateDoc) " +
                        "values('" + codeDoc + "', '" + nameDoc + "', '" + message + "',NOW()) ");
            }
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
        finally {
            //finaleClose();
        }
    }
    public void addActiv(String codeActiv, String nameActiv, String shtrihActiv,String typeActiv, String photoActiv,String contractor, String codeContractor) {
        //con = getConnection();
        ResultSet resultSet ;try {
            statement =  con.createStatement();
            resultSet = statement.executeQuery("SELECT * FROM activ WHERE number='" +codeActiv+ "'");
            resultSet.last();
            int rowCount = resultSet.getRow();
            if(rowCount>0){
               if(!photoActiv.equals("")) {
                   statement.execute("UPDATE activ SET name = '" + nameActiv + "', " +
                           "typeActiv = '" + typeActiv + "',shtrihCode = '" + shtrihActiv + "', photo = '" + photoActiv + "', contractor = '" + contractor + "', codeContractor = '" + codeContractor + "', dateActiv = NOW()  WHERE number = '" + codeActiv + "'");
               }
               else
                   statement.execute("UPDATE activ SET name = '" + nameActiv + "', " +
                           "typeActiv = '" + typeActiv + "',shtrihCode = '"+shtrihActiv+"', contractor = '"+contractor+"', codeContractor = '"+codeContractor+"', dateActiv = NOW()  WHERE number = '" + codeActiv+"'");

            }
            else {
                statement.execute("INSERT INTO activ(number, name, typeActiv,shtrihCode,photo,contractor,codeContractor,dateActiv) " +
                        "values('" + codeActiv + "', '" + nameActiv + "', '" + typeActiv + "', '" + shtrihActiv + "', '" + photoActiv + "', '" + contractor + "', '" + codeContractor + "', NOW())");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        finally {
            //finaleClose();
        }
    }
    public void addDescriptionTask(ArrayList<OTaskContainer> otasks){
        //con = getConnection();
        ResultSet resultSet ;try {
            statement =  con.createStatement();
            statement.execute("DELETE FROM descriptiontask WHERE codeTask='" +otasks.get(0).getCodeTask()+"'");
            for(OTaskContainer ot: otasks){
                statement.execute("INSERT INTO descriptiontask(name, codeTask, codeActiv) values('" + ot.getOpisanie() + "', '" + ot.getCodeTask() + "', '" + ot.getCodeActiv()+ "') ");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        finally {
            //finaleClose();
        }
    }

    public void addCompliteTask(ArrayList<CompliteTaskContainer> compliteTask){
        //con = getConnection();
        ResultSet resultSet ;try {
            statement =  con.createStatement();
            statement.execute("DELETE FROM complitetask WHERE codeTask='" +compliteTask.get(0).getCodeTask()+"'");
            for(CompliteTaskContainer ot: compliteTask){
                statement.execute("INSERT INTO complitetask(date, compliteOTask, time, codeTask, codeActiv) values('" + ot.getDate() + "', '" + ot.getCompliteOTask()+ "', '" + ot.getTime()+ "', '" + ot.getCodeTask()+ "', '" + ot.getCodeActiv()+ "') ");
            }
            

        } catch (SQLException e) {
            e.printStackTrace();
        }
        finally {
            //finaleClose();
        }
    }

    public void addUser(String login,String post,String uid,String password,String loginMobile,String rights,String codeContractor){
        //con = getConnection();
        ResultSet resultSet ;try {
            statement =  con.createStatement();
            resultSet = statement.executeQuery("SELECT * FROM user WHERE uid='" +uid+"'");
            resultSet.last();
            int rowCount = resultSet.getRow();
            if(rowCount==0){
                //statement.execute("UPDATE user SET login = '" + login + "', post = '" + post + "', uid = '" + uid + "', password = '"+ password + "', loginMobile = '"+ loginMobile + "' WHERE login = '" + login+"'");
                statement.execute("INSERT INTO user(login, post, uid,contractor,loginMobile ,rights,codeContractor,lastDate) values('" + login + "', '" + post + "', '" + uid +"', '" + password + "', '"+ loginMobile + "', '"+ rights + "', '"+ codeContractor + "', NOW())");
            }
            else{
                statement.execute("UPDATE user SET login = '" + login + "', post = '" + post + "', contractor = '"+ password + "', loginMobile = '"+ loginMobile + "', rights = '"+ rights+ "', codeContractor = '"+ codeContractor+ "' WHERE uid = '" + uid+"'");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        finally {
            //finaleClose();
        }
    }

    public void addReport(String code, String organization, String divisionOfOrganizations,String mol,String contractor,String divisionOfContractor,String molContractor,String lastDate,String shtrihCod,String nameActiv,String status) {
        //con = getConnection();
        ResultSet resultSet ;try {
            statement =  con.createStatement();
            resultSet = statement.executeQuery("SELECT * FROM report WHERE code='" +code+ "'");

            resultSet.last();
            int rowCount = resultSet.getRow();
            if(rowCount>0){
                statement.execute( "UPDATE report SET organization = '" + organization + "', " +
                        "divisionOfOrganizations = '" + divisionOfOrganizations +
                        "', " + "mol = '" + mol +
                        "', " + "contractor = '" + contractor +
                        "', " + "divisionOfContractor = '" + divisionOfContractor +
                        "', " + "molContractor = '" + molContractor +
                        "', " + "shtrihCod = '" + shtrihCod +
                        "', " + "nameActiv = '" + nameActiv +
                        "', " + "status = '" + status +
                        "',"+"lastDate = NOW() WHERE code = '" + code+"'");
            }
            else {
                statement.execute("INSERT INTO report(code, organization, divisionOfOrganizations, mol,contractor,divisionOfContractor,molContractor,shtrihCod,nameActiv,status,lastDate) " +
                        "values('" + code + "', '" + organization + "', '" + divisionOfOrganizations +  "', '" + mol +
                        "', '"+ contractor+ "', '"+ divisionOfContractor+ "', '"+ molContractor+ "', '"+ shtrihCod+ "', '"+ nameActiv+ "', '"+ status+"',NOW()) ");
            }
            

        } catch (SQLException e) {
            e.printStackTrace();
        }
        finally {
            //finaleClose();
        }
    }

    public ArrayList<DocContainer> getDocuments(){
        ArrayList<DocContainer> listDoc = new ArrayList<DocContainer>();
        //con = getConnection();
        ResultSet resultSet ;try {
            statement =  con.createStatement();
            resultSet = statement.executeQuery("SELECT * FROM documents");
            while (resultSet.next()) {
                String cod = resultSet.getString("codeDoc");
                String user = resultSet.getString("avtorDoc");
                String message = resultSet.getString("messageDoc");
                String date = resultSet.getString("dateDoc");
                listDoc.add(new DocContainer(cod,user,message,date));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        finally {
            //finaleClose();
        }
        return listDoc;
    }

    public ArrayList<RegistrContainer> getReport(String stringCont){
        ArrayList<RegistrContainer> listReport = new ArrayList<RegistrContainer>();
        //con = getConnection();
        ResultSet resultSet ;try {
            statement =  con.createStatement();
            resultSet = statement.executeQuery("SELECT * FROM report Where contractor='"+stringCont+"'");
            while (resultSet.next()) {
                String code = resultSet.getString("code");
                String organization = resultSet.getString("organization");
                String divisionOfOrganizations = resultSet.getString("divisionOfOrganizations");
                String mol = resultSet.getString("mol");
                String contractor = resultSet.getString("contractor");
                String divisionOfContractor = resultSet.getString("divisionOfContractor");
                String molContractor = resultSet.getString("molContractor");
                String lastDate = resultSet.getString("lastDate");
                String shtrihCod = resultSet.getString("shtrihCod");
                String nameActiv = resultSet.getString("nameActiv");
                String status = resultSet.getString("status");
                listReport.add(new RegistrContainer(code,organization,divisionOfOrganizations,mol,
                        contractor,divisionOfContractor,molContractor,lastDate,shtrihCod,nameActiv,status));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        finally {
            //finaleClose();
        }
        return listReport;
    }

    public ArrayList<ActivContainer> getActivs(String contr) {
        ArrayList<ActivContainer> listActiv = new ArrayList<ActivContainer>();
        //con = getConnection();
        ResultSet resultSet ;try {
            statement =  con.createStatement();
            if(contr.equals("")) {
                resultSet = statement.executeQuery("SELECT * FROM activ");
            }
            else{
                resultSet = statement.executeQuery("SELECT * FROM activ WHERE contractor ='"+contr+"'");
            }
            while (resultSet.next()) {
                String cod = resultSet.getString("number");
                String name = resultSet.getString("name");
                String contractor = resultSet.getString("contractor");
                String typeActiv = resultSet.getString("typeActiv");
                String shtrihCode = resultSet.getString("shtrihCode");
                String photo = ""/*resultSet.getString("photo")*/;
                String dateActiv = resultSet.getString("dateActiv");
                String codeContractor = resultSet.getString("codeContractor");
                listActiv.add(new ActivContainer(cod,name,contractor,typeActiv,shtrihCode,photo,dateActiv,codeContractor));
            }          

        } catch (SQLException e) {
            e.printStackTrace();
        }
        finally {
            //finaleClose();
        }
        return listActiv;
    }

    public ArrayList<TaskContainer> getTask(boolean flag, String user){
        ArrayList<TaskContainer> listTask = new ArrayList<TaskContainer>();
        ResultSet resultSet;
        //con = getConnection();
        try {
            statement =  con.createStatement();
            if(user!=null){
                System.out.println("Не нулл");
                resultSet = statement.executeQuery("SELECT * FROM task WHERE Ispolnitel = '"+user+"'");
            }
            else {
                System.out.println("Нулл");
                resultSet = statement.executeQuery("SELECT * FROM task");
            }
            while (resultSet.next()) {
                String code = resultSet.getString("Code");
                String name = resultSet.getString("Name");
                String contractor = resultSet.getString("Kontracter");
                String date = resultSet.getString("Date");
                String executer = resultSet.getString("Ispolnitel");
                String complite = resultSet.getString("Complite");
                String lastDate = resultSet.getString("lastDate");
                listTask.add(new TaskContainer(code,name,contractor,date,executer,complite,lastDate,getDescriptionTask(code,flag),getCompliteTask(code,flag)));
            }           

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return listTask;
    }

    public ArrayList<UserContainer> getUsers(String tag,String UID){
        ArrayList<UserContainer> listUser = new ArrayList<UserContainer>();
        //con = getConnection();
        ResultSet resultSet =null;
        try {
            statement = con.createStatement();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        switch (tag){
                case "get_user":
                    try {
                        resultSet = statement.executeQuery("SELECT * FROM user");
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                    break;
                case "get_lastDate":
                    try {
                        resultSet = statement.executeQuery("SELECT * FROM user WHERE uid ='"+UID+"'");
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                    break;
            }
            try {
                while (resultSet.next()) {
                    String login = resultSet.getString("login");
                    String post = resultSet.getString("post");
                    String uid = resultSet.getString("uid");
                    String lastDate = resultSet.getString("lastDate");
                    String contractor = resultSet.getString("contractor");
                    String right = resultSet.getString("rights");
                    listUser.add(new UserContainer(uid,login,post,lastDate,right,contractor));

                }

            } catch (SQLException e) {
                e.printStackTrace();
            }
            finally {
                //finaleClose();
            }

        return listUser;
    }

    public ArrayList<ContractorContainer> getContractor(){
        ArrayList<ContractorContainer> listContractor = new ArrayList<ContractorContainer>();
        //con = getConnection();
        ResultSet resultSet ;
        try {
            statement =  con.createStatement();
            resultSet = statement.executeQuery("SELECT * FROM contractor");
            while (resultSet.next()) {
                String id = resultSet.getString("code");
                String name = resultSet.getString("name");
                listContractor.add(new ContractorContainer(id,name));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        finally {
            //finaleClose();
        }
        return listContractor;
    }

    public String getContractor(String string){
        String code="";
        ResultSet resultSet ;
        try {
            statement =  con.createStatement();
            resultSet = statement.executeQuery("SELECT * FROM contractor WHERE contractor ='"+string+"'");
            while (resultSet.next()) {
                code = resultSet.getString("name");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        finally {
            //finaleClose();
        }
        return code;
    }

    public ArrayList<OTaskContainer> getDescriptionTask(String id, boolean flag){
        ArrayList<OTaskContainer> listDiscriptionTask = new ArrayList<OTaskContainer>();
        //con = getConnection();
        ResultSet resultSet ;try {
            statement =  con.createStatement();
            resultSet = statement.executeQuery("SELECT * FROM descriptiontask Where codeTask="+id);
            while (resultSet.next()) {
                String cod = resultSet.getString("code");
                String name = resultSet.getString("name");
                String codeTask = resultSet.getString("codeTask");
                String codeActiv = resultSet.getString("codeActiv");
                if(flag)
                    listDiscriptionTask.add(new OTaskContainer(cod,codeTask,name,codeActiv));
                else
                    listDiscriptionTask.add(new OTaskContainer("",codeTask,name,codeActiv));
            }
            

        } catch (SQLException e) {
            e.printStackTrace();
        }
        finally {
            //finaleClose();
        }
        return listDiscriptionTask;
    }

    public ArrayList<CompliteTaskContainer> getCompliteTask(String id, boolean flag){
        ArrayList<CompliteTaskContainer> listCompliteTask = new ArrayList<CompliteTaskContainer>();
        //con = getConnection();
        ResultSet resultSet ;try {
            statement =  con.createStatement();
            resultSet = statement.executeQuery("SELECT * FROM complitetask Where codeTask="+id);
            while (resultSet.next()) {
                String idd = resultSet.getString("id");
                String date = resultSet.getString("date");
                String compliteOTask = resultSet.getString("compliteOTask");
                String time = resultSet.getString("time");
                String codeTask = resultSet.getString("codeTask");
                String codeActiv = resultSet.getString("codeActiv");
                if(flag)
                    listCompliteTask.add(new CompliteTaskContainer(idd,date,compliteOTask,time,codeTask,codeActiv));
                else
                    listCompliteTask.add(new CompliteTaskContainer("",date,compliteOTask,time,codeTask,codeActiv));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        finally {
            //finaleClose();
        }
        return listCompliteTask;
    }

    public String getDateDocumentElement(String id){
        String date="";
        ResultSet resultSet ;try {
            statement =  con.createStatement();
            resultSet = statement.executeQuery("SELECT * FROM documents WHERE codeDoc="+id);
            while (resultSet.next()) {
                date = resultSet.getString("dateDoc");
                date = (String) date.subSequence(0,date.length()-2);
                System.out.println(date);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        finally {
            //finaleClose();
        }
        return date;
    }

    public String getDateLastChangesActiv(String id){
        String date="";
        ResultSet resultSet ;try {
            statement =  con.createStatement();
            resultSet = statement.executeQuery("SELECT * FROM activ WHERE number="+id);
            while (resultSet.next()) {
                date = resultSet.getString("dateActiv");
                date = (String) date.subSequence(0,date.length()-2);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        finally {
            //finaleClose();
        }
        return date;
    }

    public String getDateLastChangesTask(String id){
        String date="";
        ResultSet resultSet ;try {
            Statement statement =  con.createStatement();
            resultSet = statement.executeQuery("SELECT * FROM task WHERE Code="+id);
            while (resultSet.next()) {
                date = resultSet.getString("lastDate");
                date = (String) date.subSequence(0,date.length()-2);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        finally {
            //finaleClose();
        }
        return date;
    }

    public void addContragent(ArrayList<ContractorContainer> list) {
        //con = getConnection();
        ResultSet resultSet ;
        try {
            statement =  con.createStatement();
            statement.execute("DELETE FROM contractor");
            for(int i=0;i<list.size();i++)
                statement.execute("INSERT INTO contractor(code,name) " + "values('" + list.get(i).getId() + "', '" +  list.get(i).getName() + "')");

        } catch (SQLException e) {
            e.printStackTrace();
        }
        finally {
            //finaleClose();
        }

    }

    public void deleteCompliteTask() {
        //con = getConnection();
        ResultSet resultSet ;try {
            statement =  con.createStatement();
            statement.execute("DELETE FROM task WHERE Complite = 'Да'");
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
        finally {
            //finaleClose();
        }
    }

    public String getImageByShtrih(String shtrih) {
        String photo = null;
        ResultSet resultSet ;try {
            statement = con.createStatement();
            resultSet = statement.executeQuery("SELECT * FROM activ Where shtrihCode='" + shtrih + "'");
            int i = 1;
            while (resultSet.next()) {
                if(photo == null){
                    photo = resultSet.getString("photo");
                }
                i++;
            }
        }catch (SQLException e) {
            e.printStackTrace();
        }
        return photo;
    }
    /* void finaleClose(){
            ResultSet resultSet ;try {
                if (statement != null) {
                    statement.close();
                }
                if (con != null) {
                    con.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
    }*/

}
