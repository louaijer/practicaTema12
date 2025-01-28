package Game.exceptions;

public class ExistingCreatorNameException extends Exception {

    public ExistingCreatorNameException() {
        super("¡¡¡This creator name already exists!!!");
    }

    @Override
    public String getMessage() {
        ExceptionsHandler.writeLog(ExistingCreatorNameException.class, super.getMessage());
        return super.getMessage();
    }
}
