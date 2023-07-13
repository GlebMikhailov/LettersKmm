package letters.game.core.error_handling

/**
 * ApplicationException is a base class for application specific exceptions.
 * All exceptions occurred on a data are mapped to the application exceptions.
 */
abstract class ApplicationException(cause: Throwable?) : Exception(cause)

/**
 * No access to data (access token invalid or expired)
 */
class UnauthorizedException(cause: Throwable?, override val message: String? = null) :
    ApplicationException(cause)

/**
 * Received a response from the server, but it is invalid - 4xx, 5xx
 */
class ServerException(cause: Throwable?, override val message: String? = null) :
    ApplicationException(cause)

/**
 * Data transfer error
 */
abstract class TransportException(cause: Throwable?) : ApplicationException(cause)

/**
 * Failed to connect to the server and the problem is most likely on the client
 */
class NoInternetException(cause: Throwable?) : TransportException(cause)

/**
 * Failed to connect to the server and the problem is most likely on the server
 */
class NoServerResponseException(cause: Throwable?) : TransportException(cause)

/**
 *  Problems parsing json or lack of fields
 */
class DeserializationException(cause: Throwable?) : TransportException(cause)

/**
 * Could not find app for action
 */
class ExternalAppNotFoundException(cause: Throwable?) : ApplicationException(cause)

/**
 * Could not fetch current location
 */
class LocationNotAvailableException(cause: Throwable?) : ApplicationException(cause)

/**
 * Current address is not selected when it is required
 */
class CurrentAddressRequiredException : ApplicationException(null)

/**
 * Some unknown issue
 */
class UnknownException(cause: Throwable?, override val message: String) :
    ApplicationException(cause)

/**
 * Contact not foun with cursor
 */
class ContactNotFoundException(cause: Throwable?) : ApplicationException(cause)

class InvalidContactException(cause: Throwable?) : ApplicationException(cause)

class NoPermissionException(cause: Throwable?) : ApplicationException(cause)