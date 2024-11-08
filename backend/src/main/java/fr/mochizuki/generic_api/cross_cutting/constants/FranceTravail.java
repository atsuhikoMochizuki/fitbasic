package fr.mochizuki.generic_api.cross_cutting.constants;


public class FranceTravail {
    // ACCESS TOKEN
    public static final String ACCESSTOKEN_URL_GENERATION = "https://entreprise.francetravail.fr/connexion/oauth2/access_token?realm=/partenaire";
    public static final String ACCESSTOKEN_PARAM_KEY_GRANTTYPE = "grant_type";
    public static final String ACCESSTOKEN_PARAM_KEY_CLIENT_ID = "client_id";
    public static final String ACCESSTOKEN_PARAM_KEY_CLIENT_SECRET = "client_secret";
    public static final String ACCESSTOKEN_PARAM_KEY_SCOPE = "scope";
    public static final String ACCESSTOKEN_PARAM_VALUE_CLIENT_CREDENTIALS = "client_credentials";
    public static final String ACCESSTOKEN_PARAM_VALUE_OFFRESEMPLOIV2 = "api_romeov2";
    
    public static final String URL_OBTAIN_THE_CLOSEST_ROME_JOB_TITLE_FROM_FREE_TEXT_ENTRY="https://api.francetravail.io/partenaire/romeo/v2/predictionMetiers";
    public static final String ROME_URL_OBTAIN_PREDICTION = "https://api.francetravail.io/partenaire/romeo/v2/predictionMetiers";
    public static final String ROMEO_FUNCTIONAL_ID = "123456";

}
