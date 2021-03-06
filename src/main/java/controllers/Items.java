package controllers;

import org.glassfish.jersey.media.multipart.FormDataParam;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import server.Main;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

@Path("items/")
@Consumes(MediaType.MULTIPART_FORM_DATA)
@Produces(MediaType.APPLICATION_JSON)

public class Items{

    @GET
    @Path("list")
    public String ItemsList() {
        System.out.println("Invoked Items.Items.List()");
        JSONArray response = new JSONArray();
        try {
            PreparedStatement ps = Main.db.prepareStatement("SELECT ItemName FROM Items ORDER BY ItemName ASC");
            ResultSet results = ps.executeQuery();
            while (results.next()==true) {
                JSONObject row = new JSONObject();
                row.put("ItemName", results.getString(1));
                response.add(row);
            }
            return response.toString();
        } catch (Exception exception) {
            System.out.println("Database error: " + exception.getMessage());
            return "{\"Error\": \"Unable to list items.  Error code xx.\"}";
        }
    }
    @GET
    @Path("one/{ItemName}")
    public String ItemsOne() {
        System.out.println("Invoked Items.Items.One()");
        JSONArray response = new JSONArray();
        try {
            PreparedStatement ps = Main.db.prepareStatement("SELECT ItemName FROM Items WHERE ItemName = ?");
            ResultSet results = ps.executeQuery();
            while (results.next()==true) {
                JSONObject row = new JSONObject();
                row.put("ItemName", results.getString(1));
                response.add(row);
            }
            return response.toString();
        } catch (Exception exception) {
            System.out.println("Database error: " + exception.getMessage());
            return "{\"Error\": \"Unable to list items.  Error code xx.\"}";
        }
    }
    @POST
    @Path("add")
    public String ItemsAdd(@FormDataParam("ItemID") Integer ItemID, @FormDataParam("ItemName") String ItemName, @FormDataParam("SubType") String SubType, @FormDataParam("Lore") String Lore, @FormDataParam("Class") String Class, @FormDataParam("ItemType") String ItemType) {
        System.out.println("Invoked Items.ItemsAdd()");
        try {
            PreparedStatement ps = Main.db.prepareStatement("INSERT INTO Items (ItemID, ItemName, SubType, Lore, Class, ItemType) VALUES (?, ?, ?, ?, ?, ?)");
            ps.setInt(1, ItemID);
            ps.setString(2, ItemName);
            ps.setString(3, SubType);
            ps.setString(4, Lore);
            ps.setString(5, Class);
            ps.setString(6, ItemType);
            ps.execute();
            return "{\"OK\": \"Added Item.\"}";
        } catch (Exception exception) {
            System.out.println("Database error: " + exception.getMessage());
            return "{\"Error\": \"Unable to create new item, please see server console for more info.\"}";
        }

    }
    @POST
    @Path("update")
    public String updateItems(@FormDataParam("ItemID") Integer ItemID, @FormDataParam("ItemName") String ItemName, @FormDataParam("Lore") String Lore, @FormDataParam("Class") String Class) {
        try {
            System.out.println("Invoked Items.UpdateItems/update ItemID=" + ItemID);
            PreparedStatement ps = Main.db.prepareStatement("UPDATE Items SET ItemName = ? WHERE ItemID = ?");
            ps.setString(1, ItemName);
            ps.setInt(2, ItemID);
            ps.execute();
            return "{\"OK\": \"Items updated\"}";
        } catch (Exception exception) {
            System.out.println("Database error: " + exception.getMessage());
            return "{\"Error\": \"Unable to update item, please see server console for more info.\"}";
        }
    }
    @POST
    @Path("delete/{ItemID}")
    public String DeleteItem(@PathParam("ItemID") Integer ItemID) throws Exception {
        System.out.println("Invoked Items.DeleteItem()");
        if (ItemID == null) {
            throw new Exception("ItemID is missing in the HTTP request's URL.");
        }
        try {
            PreparedStatement ps = Main.db.prepareStatement("DELETE FROM Items WHERE ItemID = ?");
            ps.setInt(1, ItemID);
            ps.execute();
            return "{\"OK\": \"Item deleted\"}";
        } catch (Exception exception) {
            System.out.println("Database error: " + exception.getMessage());
            return "{\"Error\": \"Unable to delete item, please see server console for more info.\"}";
        }
    }
    @GET
    @Path("specificlist/{ItemType}")
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    @Produces(MediaType.APPLICATION_JSON)
    public String SpecificList(@PathParam("ItemType")String ItemType) {
        System.out.println("Invoked Items.Items.SpecificList()");
        JSONArray response = new JSONArray();
        try {
            PreparedStatement ps = Main.db.prepareStatement("SELECT ItemName, ItemType FROM Items WHERE ItemType = ? ORDER BY ItemName ASC");
            ps.setString(1, ItemType);
            ResultSet results = ps.executeQuery();
            while (results.next()==true) {
                JSONObject row = new JSONObject();
                row.put("ItemName", results.getString(1));
                row.put("ItemType", results.getString(2));
                response.add(row);
            }
            return response.toString();
        } catch (Exception exception) {
            System.out.println("Database error: " + exception.getMessage());
            return "{\"Error\": \"Unable to list items.  Error code xx.\"}";
        }
    }
    @GET
    @Path("get/{ItemName}")
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    @Produces(MediaType.APPLICATION_JSON)
    public String GetStats(@PathParam("ItemName") String ItemName) {
        System.out.println("Invoked Items.GetStats() with ItemName " + ItemName);
        try {
            PreparedStatement ps = Main.db.prepareStatement("SELECT ItemType, Lore, Rarity, Class, SubType FROM Items WHERE ItemName = ?");
            ps.setString(1, ItemName);
            ResultSet results = ps.executeQuery();
            JSONObject response = new JSONObject();
            if (results.next()== true) {
                response.put("ItemName", ItemName);
                response.put("ItemType", results.getString(1));
                response.put("Lore", results.getString(2));
                response.put("Rarity", results.getString(3));
                response.put("Class", results.getString(4));
                response.put("SubType", results.getString(5));
            }
            return response.toString();
        } catch (Exception exception) {
            System.out.println("Database error: " + exception.getMessage());
            return "{\"Error\": \"Unable to get item, please see server console for more info.\"}";
        }
    }
    @GET
    @Path("getID/{ItemName}")
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    @Produces(MediaType.APPLICATION_JSON)
    public String IDFetch(@PathParam("ItemName") String ItemName) {
        System.out.println("Invoked Items.GetID() with ItemName " + ItemName);
        try {
            PreparedStatement ps = Main.db.prepareStatement("SELECT ItemID FROM Items WHERE ItemName = ?");
            ps.setString(1, ItemName);
            ResultSet results = ps.executeQuery();
            JSONObject response = new JSONObject();
            if (results.next()== true) {
                response.put("ItemID", results.getInt(1));
            }
            return response.toString();
        } catch (Exception exception) {
            System.out.println("Database error: " + exception.getMessage());
            return "{\"Error\": \"Unable to get item, please see server console for more info.\"}";
        }
    }
    @GET
    @Path("search/{ItemName}")
    public String ItemSearch() {
        System.out.println("Invoked Items.Items.One()");
        JSONArray response = new JSONArray();
        try {
            PreparedStatement ps = Main.db.prepareStatement("SELECT ItemName FROM Items WHERE ItemName LIKE ?");
            ResultSet results = ps.executeQuery();
            while (results.next()==true) {
                JSONObject row = new JSONObject();
                row.put("ItemName", results.getString(1));
                response.add(row);
            }
            return response.toString();
        } catch (Exception exception) {
            System.out.println("Database error: " + exception.getMessage());
            return "{\"Error\": \"Unable to list items.  Error code xx.\"}";
        }
    }
}


