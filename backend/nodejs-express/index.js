const express = require("express");
const morgan = require("morgan");
const passport = require("passport");
const config = require('./config');
const BearerStrategy = require('passport-azure-ad').BearerStrategy;

// A simple check for clientID placeholder (do not replace with you client id)
if (config.clientID === 'YOUR_CLIENT_ID') {
    console.error("Please update 'options' with the client id (application id) of your application");
    return;
}

const bearerStrategy = new BearerStrategy(config,
    function (token, done) {
        // Send user info using the second argument
        done(null, {}, token);
    }
);

const app = express();

app.use(morgan('dev'));
app.use(passport.initialize());

passport.use(bearerStrategy);

//enable CORS
app.use((req, res, next) => {
    res.header("Access-Control-Allow-Origin", "*");
    res.header("Access-Control-Allow-Headers", "Authorization, Origin, X-Requested-With, Content-Type, Accept");
    next();
});

// API endpoint
app.get("/profile",
    passport.authenticate('oauth-bearer', {session: false}),
    (req, res) => {
        console.log('User info: ', req.user);
        console.log('Validated claims: ', req.authInfo);
        console.log(req.authInfo['scp']);
        
        if ('scp' in req.authInfo && req.authInfo['scp'].split(" ").indexOf("b2cdemo.read") >= 0) {
            // Service relies on the name claim.  
            res.status(200).json({'name': req.authInfo['given_name']});
        } else {
            console.log("Invalid Scope, 403");
            res.status(403).json({'error': 'insufficient_scope'}); 
        }
    }
);

const port = process.env.PORT || 9000;

app.listen(port, () => {
    console.log("Listening on port " + port);
});
