package Game.exceptions;

public class PlayerCreatorNameConflictException extends Exception {

    public PlayerCreatorNameConflictException() {
        super("¡¡¡You cannot create a player with existing creator name!!!");
    }

    @Override
    public String getMessage() {
        ExceptionsHandler.writeLog(PlayerCreatorNameConflictException.class, super.getMessage());
        return super.getMessage();
    }
}
