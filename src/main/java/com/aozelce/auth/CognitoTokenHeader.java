package com.aozelce.auth;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * The type Cognito token header.
 */
public class CognitoTokenHeader{

	@JsonProperty("kid")
	private String kid;

	@JsonProperty("alg")
	private String alg;

    /**
     * Get kid string.
     *
     * @return the string
     */
    public String getKid(){
		return kid;
	}

    /**
     * Get alg string.
     *
     * @return the string
     */
    public String getAlg(){
		return alg;
	}
}