// Update these four variables with values from your B2C tenant in the Azure portal
const clientID = "34e749f7-1627-4b65-afb6-05d05074b676"; // Application (client) ID of your API's application registration
const b2cDomainHost = "vidolin.b2clogin.com";
const tenantId = "vidolin.onmicrosoft.com"; // Alternatively, you can use your Directory (tenant) ID (a GUID)
const policyName = "B2C_1_SignIn_v2";

const config = {
    identityMetadata: "https://" + b2cDomainHost + "/" + tenantId + "/" + policyName + "/v2.0/.well-known/openid-configuration/",
    clientID: clientID,
    policyName: policyName,
    isB2C: true,
    validateIssuer: false,
    loggingLevel: 'info',
    loggingNoPII: false,
    passReqToCallback: false
}

module.exports = config;