package App;

public class jsonCuerpoMsg {
    String doc;
    String paciente;

    public jsonCuerpoMsg(String doc, String paciente) {
        this.doc = doc;
        this.paciente = paciente;
    }

    public String getDoc() {
        return doc;
    }

    public void setDoc(String doc) {
        this.doc = doc;
    }

    public String getPaciente() {
        return paciente;
    }

    public void setPaciente(String paciente) {
        this.paciente = paciente;
    }

    @Override
    public String
    toString() {
        return "{" +
                "doc:'" + doc + '\'' +
                ", paciente:'" + paciente + '\'' +
                '}';
    }
}
