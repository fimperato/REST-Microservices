
public class AbcBar {

    public String getAbcBarWord(int number) {
        StringBuilder AbcBarWord = new StringBuilder();

        if (number % 3 == 0) {
            AbcBarWord.append("Abc");
        }

        if (number % 5 == 0) {
            AbcBarWord.append("Bar");
        }

        return (AbcBarWord.length() == 0) ? null : AbcBarWord.toString();
    }
}
