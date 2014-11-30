package com.clinet;

import java.io.Serializable;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

import com.clinet.model.AccountDTO;
import com.interf.test.CommonRemote;
import com.interf.test.Constant;
import com.interf.test.Messenger;
import com.interf.test.MessgerInterface;

public class TestClient implements Serializable
{

	//TODO: http://www.ejbtutorial.com/programming/java-rmi-example-group-chat-implementation
	
	public static void main(String[] args){
        try
        {
            new TestClient().start();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    private void start() throws Exception{
        Registry registry = LocateRegistry.getRegistry("localhost", Registry.REGISTRY_PORT);
        CommonRemote remote = (CommonRemote) registry.lookup(Constant.RMI_ID);
        System.out.println("connected to server");

        AccountDTO acc1 = new AccountDTO("abc", "123");

        System.out.println("try to login with user: abc: " + remote.isLoginValid(acc1));
        System.out.println("try to login with user: test: " + remote.isLoginValid(new AccountDTO("def", "def")));

        while(true){
            try
            {
                System.out.println("fuck");
                remote.broadcastMessage("abc", new Messenger("fucker"));
                Thread.sleep(2000);
            }
            catch (InterruptedException e)
            {
                e.printStackTrace();
            }
        }
    }
}
