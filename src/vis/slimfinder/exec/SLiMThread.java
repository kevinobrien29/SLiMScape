package vis.slimfinder.exec;

public abstract class SLiMThread extends Thread{
	public abstract void cancel();
	
	@Override
	public void run()
	{
	}
}
