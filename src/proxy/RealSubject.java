package proxy;

public class RealSubject implements Subject{

	@Override
	public void rent() {
		// TODO Auto-generated method stub
		System.out.println("rent....");
		
	}

	@Override
	public void hello(String str) {
		// TODO Auto-generated method stub
		System.out.println("hello:"+str);
		
	}

}
