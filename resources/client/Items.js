"use strict";
function getItemsList() {
    //debugger;
    console.log("Invoked getItemsList");     //console.log your BFF for debugging client side - also use debugger statement
    const url = "/items/list";    		// API method on web server will be in Users class, method list
    fetch(url, {
        method: "GET",				//Get method
    }).then(response => {
        return response.json();                 //return response as JSON
}).then(response => {
        if (response.hasOwnProperty("Error")) { //checks if response from the web server has an "Error"
        alert(JSON.stringify(response));    // if it does, convert JSON object to string and alert (pop up window)
    } else {
        formatItemsList(response);          //this function will create an HTML table of the data (as per previous lesson)
    }
});
}


function formatItemsList(myJSONArray){
    let dataHTML = "";
    for (let item of myJSONArray) {
        dataHTML += "<tr><td>" + "<button class='resultbutton' onclick='clearTable(); setItemStats(" + JSON.stringify(item.ItemName) + ")'>" + item.ItemName +"</button>" + "<tr><td>";
    }
    document.getElementById("ItemsTable").innerHTML = dataHTML;
}



function getArmourList() {
    //debugger;
    console.log("Invoked getArmourList");     //console.log your BFF for debugging client side - also use debugger statement
    const url = "/items/specificlist/Armour";    		// API method on web server will be in Users class, method list
    fetch(url, {
        method: "GET",				//Get method
    }).then(response => {
        return response.json();                 //return response as JSON
    }).then(response => {
        if (response.hasOwnProperty("Error")) { //checks if response from the web server has an "Error"
            alert(JSON.stringify(response));    // if it does, convert JSON object to string and alert (pop up window)
        } else {
            formatItemsList(response);
        }
    });
}





function getWeaponsList() {
    //debugger;
    String
    let searchValue;
    searchValue = 'Weapon';
    console.log("Invoked getWeaponsList");     //console.log your BFF for debugging client side - also use debugger statement
    const url = "/items/specificlist/Weapon";    		// API method on web server will be in Users class, method list
    fetch(url, {
        method: "GET",				//Get method
    }).then(response => {
        return response.json();                 //return response as JSON
    }).then(response => {
        if (response.hasOwnProperty("Error")) { //checks if response from the web server has an "Error"
            alert(JSON.stringify(response));    // if it does, convert JSON object to string and alert (pop up window)
        } else {
            formatItemsList(response);          //this function will create an HTML table of the data (as per previous lesson)
        }
    });
}


function setItemStats(Name){
    let dataHTML = "";
    let dataHTML2 = "";
    let Table = document.getElementById("StatName");
    dataHTML = "<a>" + Name +"</a>";
    Table.innerHTML = dataHTML;
    getItemID(Name);
    switch (Name){
        case "Ashen Wake":
            document.getElementById("Image").src = "img/AshenWake.jpg";
            break;
        case "ACD0 Feedback Fence":
            document.getElementById("Image").src = "img/ACD0FeedbackFence.jpg";
            break;
        case "Ace of Spades":
            document.getElementById("Image").src = "img/Ace.jpg";
            break;
        case "Actium War Rig":
            document.getElementById("Image").src = "img/Actium.jpg";
            break;
        case "Aeon Safe":
            document.getElementById("Image").src = "img/Safe.jpg";
            break;
        case "Aeon Soul":
            document.getElementById("Image").src = "img/soul.jpg";
            break;
        case "Aeon Swift":
            document.getElementById("Image").src = "img/swift.jpg";
            break;
        default:
            document.getElementById("Image").src = "img/Image.jpg";
    }
    getItemStats(Name);
}
function getItemID(Name){
    //debugger;
    console.log("Invoked getItemID()");     //console.log your BFF for debugging client side - also use debugger statement
    const url = "/items/getID/" + Name;    		// API method on web server will be in Users class, method list
    fetch(url, {
        method: "GET",				//Get method
    }).then(response => {
        return response.json();                 //return response as JSON
    }).then(response => {
        if (response.hasOwnProperty("Error")) { //checks if response from the web server has an "Error"
            alert(JSON.stringify(response));    // if it does, convert JSON object to string and alert (pop up window)
        } else {
            formatImage(response);          //this function will create an HTML table of the data (as per previous lesson)
        }
    });
}
function formatImage(ItemID){
    let dataHTML = "";
    dataHTML = "img/" + ItemID.ItemID +".jpg";
    document.getElementById("Image").src = dataHTML;
}
function getItemStats(Name){
    //debugger;
    console.log("Invoked getItemStats()");     //console.log your BFF for debugging client side - also use debugger statement
    const url = "/items/get/" + Name;    		// API method on web server will be in Users class, method list
    fetch(url, {
        method: "GET",				//Get method
    }).then(response => {
        return response.json();                 //return response as JSON
    }).then(response => {
        if (response.hasOwnProperty("Error")) { //checks if response from the web server has an "Error"
            alert(JSON.stringify(response));    // if it does, convert JSON object to string and alert (pop up window)
        } else {
            formatItemStats(response);          //this function will create an HTML table of the data (as per previous lesson)
        }
    });
}
function formatItemStats(stats){
    let dataHTML2 = "";
    let dataHTML3 = "";
    let dataHTML4 = "";
    let dataHTML5 = "";
    dataHTML2 += "<tr><td>" + stats.Lore + "<tr><td>";
    dataHTML3 += "<tr><td>" + stats.Rarity + " "+ stats.SubType + "<tr><td>";
    dataHTML4 += "<tr><td>" + stats.Class + "<tr><td>";
    dataHTML5 += "<tr><td>" + stats.ItemType + "<tr><td>";
    document.getElementById("ItemType").innerHTML = dataHTML3;
    document.getElementById("Lore").innerHTML = dataHTML2;
    document.getElementById("Class").innerHTML =  dataHTML4;
    switch(stats.ItemType){
        case "Armour":
            document.getElementById("Mod1").src = "img/Discipline.png";
            document.getElementById("Mod2").src = "img/Strength.png";
            document.getElementById("Mod3").src = "img/Intellect.png";
            document.getElementById("Mod4").src = "img/Resilience.png";
            document.getElementById("Mod5").src = "img/Recovery.png";
            document.getElementById("Information Page").innerHTML= <a id="Information Page" href="ArmourMods.html">Click Here For More Information On Armour Mods</a>;
            break;
        case "Weapon":
            document.getElementById("Mod1").src = "img/Minor.jpg";
            document.getElementById("Mod2").src = "img/Major.jpg";
            document.getElementById("Mod3").src = "img/Boss.jpg";
            document.getElementById("Mod4").src = "img/Backup.jpg";
            document.getElementById("Mod5").src = "img/Radar.jpg";
            document.getElementById("Information Page").innerHTML= <a id="Information Page" href="WeaponMods.html">Click Here For More Information On Weapon Mods</a>;
            break;
    }
}

function clearTable(){
    let Table = document.getElementById("ItemsTable");
    Table.innerHTML = "";
    document.getElementById("ItemType").innerHTML = "";
    document.getElementById("Lore").innerHTML = "";
    document.getElementById("StatName").innerHTML = "";
    document.getElementById("Class").innerHTML = "";
}
function changeImage(){
    let data = "";
    data += "<img src='img/AshenWake.jpg'>";
    document.getElementById("Image").src = "img/AshenWake.jpg";
}