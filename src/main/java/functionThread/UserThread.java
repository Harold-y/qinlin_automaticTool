package functionThread;

import utils.UserSQL;

public class UserThread implements Runnable{

    UserSQL sql = new UserSQL();
    public boolean flag = true;
    private String name;

    public UserThread()
    {
        this("UnNamed UserThread");
    }

    public UserThread(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public void run() {
        try {
            while(flag)
            {
                int count = sql.selectUserAmount();
                if(count >= 150)
                {
                    flag = false;
                    System.out.println("User Thread "+name+": User Amount is Too High, Terminate User Thread.");
                }
                int result = sql.insertIntoUser();
                if(result == 1)
                    System.out.println("Successfully inserted one Auto User!");
                Thread.sleep(50000);
            }
            if(!flag)
            {
                sql.closeConnection();
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
