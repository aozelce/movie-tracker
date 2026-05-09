package com.aozelce.auth;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * The type Keys item.
 */
public class KeysItem{

	@JsonProperty("kty")
	private String kty;

	@JsonProperty("e")
	private String E;

	@JsonProperty("use")
	private String use;

	@JsonProperty("kid")
	private String kid;

	@JsonProperty("alg")
	private String alg;

	@JsonProperty("n")
	private String N;

    /**
     * Get kty string.
     *
     * @return the string
     */
    public String getKty(){
		return kty;
	}

    /**
     * Get e string.
     *
     * @return the string
     */
    public String getE(){
		return E;
	}

    /**
     * Get use string.
     *
     * @return the string
     */
    public String getUse(){
		return use;
	}

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

    /**
     * Get n string.
     *
     * @return the string
     */
    public String getN(){
		return N;
	}
}