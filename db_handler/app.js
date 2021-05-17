const path = require('path');
const express = require('express');
const fs = require('fs');
const readLine = require('readline');
const app = express();
const server = require('http').createServer(app);
const io = require('socket.io')(server);

// Firebase
const admin = require("firebase-admin");
const serviceAccount = require("./seat-mapy-firebase-adminsdk-9b7fx-4c75ed25ff.json");

admin.initializeApp({
    credential: admin.credential.cert(serviceAccount),
    databaseURL: "https://seat-mapy-default-rtdb.asia-southeast1.firebasedatabase.app/"
});

app.use(express.static('.'));
app.get('/', (req, res) => {
    res.sendFile(path.join(__dirname, '/index.html'));
});

io.on('connection', (socket) => {
    console.log("Connected");

    socket.on('disconnect', (_) => {
        console.log("Disconnected");
    });
});

var dbRef = admin.database().ref("Seats").child("Bus");

const sensorData = [0, 0, 0, 0, 0, 0];
setInterval(() => {
    readData().then(() => {
        io.emit('data', sensorData);
        for (let i = 0; i < sensorData.length; i++) {
            dbRef.child(`seat ${i + 1}`).set(sensorData[i]);
        }
    });
}, 1000);

async function readData() {
    const stream = fs.createReadStream("serial.txt");

    let lineReader = readLine.createInterface({
        input: stream
    });

    lineReader.on('line', function (line) {
        let data = line.split(';');
        for (let i = 0; i < data.length; i++) {
            if (data[i] === "OCCUPIED") {
                sensorData[i] = 1;
            } else {
                sensorData[i] = 0;
            }
        }
    });
}

server.listen(5000, () => {
    console.log('Listening on 5000');
});
