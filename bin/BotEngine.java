import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;


public class BotEngine{
	static class Queue{
		class QueueNode{
			QueueNode before,after;
			char c;
		}
		QueueNode first,last;
		void append(char c){
			QueueNode d=new QueueNode();
			d.c=c;
			if(last!=null)last.after=d;
			d.before=last;
			last=d;
			if(first==null)first=d;
		}
		void duplicate(){
			if(first==null)return;
			QueueNode d=new QueueNode();
			d.c=first.c;
			first.before=d;
			d.after=first;
			first=d;
		}
		void rotate(){
			if(first==last)return;
			QueueNode d=first;
			first=first.after;
			first.before=null;
			last.after=d;
			d.before=last;
			last=d;
			d.after=null;
		}
		void unrotate(){
			if(first==last)return;
			QueueNode d=last;
			last=last.before;
			last.after=null;
			d.after=first;
			d.before=null;
			first.before=d;
			first=d;
		}
		char poll(){
			QueueNode d=first;
			if(first.after!=null)first.after.before=null;
			first=first.after;
			return d.c;
		}
		void reverse(){
			Stack<Character>c=new Stack<>();
			while(first!=null){
				c.add(poll());
			}
			while(!c.isEmpty())append(c.pop());
		}
		public String toString(){
			StringBuilder s=new StringBuilder();
			QueueNode d=first;
			while(d!=null){
				s.append(d.c);
				d=d.after;
			}
			return s.toString();
		}
		public Queue copy(){
			Queue c=new Queue();
			QueueNode d=first;
			while(d!=null){
				c.append(d.c);
				d=d.after;
			}
			return c;
		}
		public char peek(){
			return first==null?'\0':first.c;
		}
		public boolean isEmpty(){
			return first==null;
		}
	}
	static enum Dir{
		N,S,E,W;
		static Dir flip(Dir d){
			if(d==N)return S;
			if(d==S)return N;
			if(d==E)return W;
			return E;
		}
		static Dir cw(Dir d){
			if(d==N)return E;
			if(d==S)return W;
			if(d==E)return S;
			return N;
		}
		static Dir ccw(Dir d){
			return flip(cw(d));
		}
	}
	static class Bot{
		int x=-1,y=0;
		Queue q=new Queue();
		Dir d=Dir.E;
		Bot copy(){
			Bot b=new Bot();
			b.x=x;
			b.y=y;
			b.q=q.copy();
			b.d=Dir.flip(d);
			return b;
		}
	}
	static List<Bot>bots=new ArrayList<>();
	static int x(int x,Dir d){
		if(d==Dir.E)return x+1;
		if(d==Dir.W)return x-1;
		return x;
	}
	static int y(int x,Dir d){
		if(d==Dir.S)return x+1;
		if(d==Dir.N)return x-1;
		return x;
	}
	@SuppressWarnings("resource")
	public static void main(String[]args) throws FileNotFoundException{
		bots.add(new Bot());
		Scanner in=new Scanner(System.in);
		Scanner read=new Scanner(new File((args.length>0?args[0]:in.nextLine())+".bot"));
		List<char[]>linez=new ArrayList<>();
		while(read.hasNext()){
			linez.add(read.nextLine().toCharArray());
		}
		read.close();
		int l=1;
		for(char[]c:linez){
			l=Math.max(l,c.length);
		}
		for(int i=0;i<linez.size();i++){
			char[]c;
			linez.set(i,c=Arrays.copyOf(linez.get(i),l));
			for(int j=0;j<l;j++){
				if(c[j]==0)c[j]=' ';
			}
		}
		int h=linez.size();
		while(true){
			if(bots.isEmpty())System.exit(0);
			k:for(Bot b:bots.toArray(new Bot[0])){
				int x=x(b.x,b.d),y=y(b.y,b.d);
				if(x<0||x>=l||y<0||y>=h){
					bots.remove(b);
					continue;
				}
				for(Bot c:bots){
					if(c.x==x&&c.y==y)continue k;
				}
				b.x=x;
				b.y=y;
				if(linez.get(y)[x]=='C')bots.add(b.copy());
				if(linez.get(y)[x]=='X')bots.remove(b);
				if(linez.get(y)[x]=='T'){
					bots.remove(b);
					System.out.println("TRUE");
				}
				if(linez.get(y)[x]=='F'){
					bots.remove(b);
					System.out.println("FALSE");
				}
				if(linez.get(y)[x]=='P'){
					bots.remove(b);
					System.out.println(b.q);
				}
				if(linez.get(y)[x]=='Z')System.exit(0);
				if(linez.get(y)[x]=='|')b.d=Dir.flip(b.d);
				if(linez.get(y)[x]=='>')b.d=Dir.E;
				if(linez.get(y)[x]=='<')b.d=Dir.W;
				if(linez.get(y)[x]=='^')b.d=Dir.N;
				if(linez.get(y)[x]=='v')b.d=Dir.S;
				if(linez.get(y)[x]=='E')if(b.q.isEmpty())b.d=Dir.cw(b.d);
				if(linez.get(y)[x]=='r')b.d=Dir.cw(b.d);
				if(linez.get(y)[x]=='l')b.d=Dir.ccw(b.d);
				if(linez.get(y)[x]=='R')b.q.reverse();
				if(linez.get(y)[x]=='I')for(char c:in.nextLine().toCharArray())b.q.append(c);
				if(linez.get(y)[x]=='d')b.q.duplicate();
				if(linez.get(y)[x]=='~')b.q.rotate();
				if(linez.get(y)[x]=='@')b.q.unrotate();
				int x1=x(x,Dir.ccw(b.d));
				int y1=y(y,Dir.ccw(b.d));
				if(linez.get(y)[x]=='S'){
					char c=linez.get(y1)[x1];
					if(b.q.peek()==c){
						b.q.poll();
						b.d=Dir.cw(b.d);
					}
				}
				if(linez.get(y)[x]=='e'){
					char c=linez.get(y1)[x1];
					b.q.append(c);
				}
			}
		}
	}
}