package ie;

public class BaseOutput
{
    boolean success;
    String data;
    public BaseOutput(boolean success, String data)
    {
        this.success = success;
        this.data = data;
    }

    public BaseOutput()
    {
    }

    public void setSuccess(boolean success) { this.success = success; }
    public void setData(String data) { this.data = data; }

    public boolean getSuccess()
    {
        return this.success;
    }
    public String getData()
    {
        return data;
    }
}
