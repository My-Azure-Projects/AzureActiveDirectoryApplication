package com.vidolima.userimport;

import com.microsoft.graph.auth.confidentialClient.ClientCredentialProvider;
import com.microsoft.graph.auth.enums.NationalCloud;
import com.microsoft.graph.models.extensions.*;
import com.microsoft.graph.requests.extensions.GraphServiceClient;
import com.microsoft.graph.requests.extensions.IUserCollectionRequestBuilder;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@SpringBootApplication
public class UserimportApplication {

	private final static String CLIENT_ID = "";
	private final static List<String> SCOPES = Arrays.asList(
			"https://graph.microsoft.com/.default"
	);
	private final static String CLIENT_SECRET = "";
	private final static String TENANT_GUID = ""; // got this id at Azure Portal > Azure Active Directory > Tenant properties
	private final static NationalCloud NATIONAL_CLOUD = null;

	public static void main(String[] args) {
		SpringApplication.run(UserimportApplication.class, args);
		run();
	}

	public static void run() {
		ClientCredentialProvider authProvider =
				new ClientCredentialProvider(CLIENT_ID, SCOPES, CLIENT_SECRET, TENANT_GUID, NATIONAL_CLOUD);
		IGraphServiceClient graphClient = GraphServiceClient
				.builder()
				.authenticationProvider(authProvider)
				.buildClient();

		IUserCollectionRequestBuilder usersRequest = graphClient.users();
		usersRequest.buildRequest().post(getFakeUser());
	}

	private static User getFakeUser() {
		User user = new User();
		user.accountEnabled = true;
		user.displayName = "Marcos Vidolin";
		user.givenName = "Marcos A. Vidolin de Lima";
		user.userPrincipalName = "vidola@vidolin.onmicrosoft.com";
		user.mail = "vidola@gmail.com";
		user.mailNickname = "Vidolin";
		user.surname = "vidola";

		PasswordProfile pass = new PasswordProfile();
		pass.forceChangePasswordNextSignIn = false;
		pass.forceChangePasswordNextSignInWithMfa = false;
		pass.password = "1234";
		user.passwordProfile = pass;
		user.passwordPolicies = "DisablePasswordExpiration, DisableStrongPassword";

		ObjectIdentity identity = new ObjectIdentity();
		identity.issuer = "vidolin.onmicrosoft.com";
		identity.signInType = "emailAddress"; // emailAddress, userName or federated
		identity.issuerAssignedId = "vidola@gmail.com";
		
		user.identities = Collections.singletonList(identity);
		
		return user;
	}

}
