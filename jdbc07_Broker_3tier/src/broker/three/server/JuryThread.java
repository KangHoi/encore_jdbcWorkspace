package broker.three.server;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;

import broker.three.exception.DuplicateSSNException;
import broker.three.exception.InvalidTransactionException;
import broker.three.exception.RecordNotFoundException;
import broker.three.shares.Command;
import broker.three.shares.Result;
import broker.three.vo.CustomerRec;
import broker.three.vo.StockRec;

public class JuryThread extends Thread{
	private Socket s;
	private Database db;
	private ObjectInputStream ois;
	private ObjectOutputStream oos;
	private Command cmd;
	
	JuryThread(Socket s, Database db){
		System.out.println("JuryThread... 생성자");
		this.s =s;
		this.db = db;
		try {
			ois = new ObjectInputStream(s.getInputStream());
			oos = new ObjectOutputStream(s.getOutputStream());
		}catch(Exception e) {
			
		}
		System.out.println("Jury... Creating...");
	}
	/*
	 *  1. protocol이 보낸 도시락 받는다.
	 *  2. 도시락 깐다... 10~90
	 *  
	 *  3. 10~90 상수값에 해당하는 Database 메소드를 직접 호출한다.
	 *  	이 떄 인자값 알아서 잘 넣어준다.. Switch문 사용!
	 *  
	 *  4. DB갔다가 나온 결과값 다시 도시락에 싸서 Protocol로 보낸다
	 */
	public void run() {
		System.out.println("run()... 진입..");
		
		while(true) {
			try {
				//1. 도시락 받는다
				cmd = (Command)ois.readObject();
				System.out.println("cmd... Jury readObject()...!");
			}catch(Exception e) {
				
				}
			//2. 도시락깐다... Data Unpack...Getter
			int comm = cmd.getCommandValue();
			String[ ] args = cmd.getArgs();
			Result r = cmd.getResults();
			
			
			//3. comm 값에 따라 Database의 메소드 각각 호출
			switch(comm) {// 실수형은 인자값 안됨.
			case Command.BUYSHARES:
				try {
					db.buyShares(args[0], args[1], Integer.parseInt(args[2]));
					
					//이 부분은 DB Access하고 난 후의 코드라인이 된다.
					r.setStatus(0);
				}catch(Exception e) {
					
				}
			break;
			
			case Command.SELLSHARES:
				try {
					db.sellShares(args[0], args[1], Integer.parseInt(args[2]));
					
					//이 부분은 DB Access하고 난 후의 코드라인이 된다.
					r.setStatus(0);
				}catch(RecordNotFoundException e) {
					//이 부분으로 DB Access하고 난 후의 코드가 돌아왔다면 sell할 때 문제가 발생했다는 의미
					System.out.println("팔 주식이 존재하지 않아요... Jury catch...");
					r.setStatus(-1);
					
				} catch(InvalidTransactionException e) {
					System.out.println("팔려는 주식이 넘 많아요... Jury catch...");
					r.setStatus(-3);
				} catch(Exception e) {
					
				}
			break;
			
			case Command.GETALLSTOCK:
				try {
					ArrayList<StockRec> list = db.getAllStocks();
					
					//이 부분은 DB Access하고 난 후의 코드라인이 된다.
					r.setStatus(0);
					r.add(list);
				} catch(Exception e) {
					
				}
			break;
			
		//만들어진 비즈니스 로직 없음.	
//			case Command.GETSTOCKPRICE:
//				try {
//					db.sellShares(args[0], args[1], Integer.parseInt(args[2]));
//					
//					//이 부분은 DB Access하고 난 후의 코드라인이 된다.
//					r.setStatus(0);
//			
//				} catch(Exception e) {
//					
//				}
//			break;
			
			case Command.GETALLCUSTOMER:
				try {
					ArrayList<CustomerRec> list = db.getAllCustomers();
					System.out.println("GETALLCUSTOMER... 호출되었다");
					
					//이 부분은 DB Access하고 난 후의 코드라인이 된다.
					r.setStatus(0);
					r.add(list);
			
				} catch(Exception e) {
					
				}
			break;
			
			case Command.GETCUSTOMER:
				try {
					CustomerRec cust = db.getCustomer(args[0]);
					
					//이 부분은 DB Access하고 난 후의 코드라인이 된다.
					r.setStatus(0);
					r.add(cust);
				} catch(Exception e) {
					
				}
			break;
			
			case Command.ADDCUSTOMER:
				try {
					CustomerRec cust = new CustomerRec(args[0], args[1], args[2]);
					db.addCustomer(cust);
					
					//이 부분은 DB Access하고 난 후의 코드라인이 된다.
					r.setStatus(0);
				} catch(DuplicateSSNException e) {
					System.out.println("이미 존재하는 고객이에요... Jury catch...");
					r.setStatus(-2);
				} catch(Exception e) {
					
				}
			break;
			
			case Command.DELETECUSTOMER:
				try {
					db.deleteCustomer(args[0]);//
					
					//이 부분은 DB Access하고 난 후의 코드라인이 된다.
					r.setStatus(0);
				} catch(Exception e) {
					
				}
			break;
			
			case Command.UPDATECUSTOMER:
				try {
					CustomerRec cust = new CustomerRec(args[0], args[1], args[2]);
					db.updateCustomer(cust);
					
					//이 부분은 DB Access하고 난 후의 코드라인이 된다.
					r.setStatus(0);
				} catch(RecordNotFoundException e) {
					System.out.println("존재하지 않는 고객입니다... Jury catch...");
					r.setStatus(-1);
				} catch(Exception e) {
					
				}
			break;
			}//switch
			
			try {
				oos.writeObject(cmd); //다싼 도시락 집어넣기
			}catch(IOException e) {
				
			}
		}//while
		
	}//run
}//class
