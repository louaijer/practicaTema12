package Game;

import Game.exceptions.ExistingCreatorNameException;

import java.io.File;

public class Creator {
    private String name;

    public Creator(String name) throws ExistingCreatorNameException {
        File file = new File("./juegoAdivinar/" + name + "/juego_de_" + name + ".txt");
        if(file.exists()) {
            throw new ExistingCreatorNameException();
        } else  {
            this.name = name;
        }
    }

    public String getName() { return name; }
}
