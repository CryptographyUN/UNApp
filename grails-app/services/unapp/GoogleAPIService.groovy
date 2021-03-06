package unapp

import grails.transaction.Transactional
import org.scribe.model.Token
import grails.converters.JSON

@Transactional
class GoogleAPIService {
    def oauthService

    def getJSON(Token googleAccessToken) {
        def googleResource = oauthService.getGoogleResource(googleAccessToken, "https://www.googleapis.com/oauth2/v2/userinfo")
        def googleResponse = JSON.parse(googleResource?.getBody())
        return googleResponse
    }

    def get(Token googleAccessToken) {
        def googleResponse = getJSON(googleAccessToken)
        Map userData = [:]
        googleResponse.each { k, v ->
            userData[k] = v
        }
        return userData
    }
}
