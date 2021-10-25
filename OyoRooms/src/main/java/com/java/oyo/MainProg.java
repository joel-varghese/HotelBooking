package com.java.oyo;

import java.util.Date;
import java.util.List;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.AnnotationConfiguration;

public class MainProg {

	SessionFactory sf = new AnnotationConfiguration().configure().buildSessionFactory();
	Session s = sf.openSession();
	Query q;
	
	public String addroom(Room room) {

	String roomid = "Select count(*) FROM Room";
	Query countQuery = s.createQuery(roomid);
	long num = (Long)countQuery.iterate().next();
	Room rr = new Room();
	rr.setType(room.getType());
	rr.setStatus(room.getStatus());
	rr.setCostperday(room.getCostperday());
	if(countQuery==null) {
		rr.setRoomid("R001");
	} else {
		num+=1;
		String res = "R00" + Long.toString(num);
		rr.setRoomid(res);
		}
	Transaction t = s.beginTransaction();

	s.save(rr);
	t.commit();
	return "Room Inserted Successfully";
	}
	
	public String genbook() {
		String bookid = "Select count(*) FROM Booking";
		Query countQuery = s.createQuery(bookid);
		long num = (Long)countQuery.iterate().next();
		String res;
		if(countQuery==null) {
			res = "B001";
		} else {
			num+=1;
			res = "B00" + Long.toString(num);
		}
		return res;
	}
	
	public List<Room> showrooms(){
		q = s.createQuery("from Room where Status='AVAILABLE'");
		List<Room> lst = q.list();
		if (lst.isEmpty()) {
			return null;
		} else {
			return lst;
		}
	}
	
	public String addbooking(Booking book) {
		Transaction t = s.beginTransaction();

		s.save(book);
		t.commit();
		
		t=s.beginTransaction();
		q = s.createQuery("from Room where roomid='"+book.getRoomid()+"'");
		List<Room> lst = q.list();
		Room r1 = new Room();
		for(Room rm:lst) {
			r1 = rm;
			r1.setStatus(Status.BOOKED);
		}
		s.save(r1);
		t.commit();
		return "Room Booked Successfully";
	}
	public int getnodays(String roomid) {
		q = s.createQuery("from Booking where RoomID='" + roomid + "'");
		List<Booking> lst = q.list();
		int days=0;
		for(Booking bm : lst) {
		 long ms = bm.getChkoutdate().getTime() - bm.getChkindate().getTime();
		    long m = ms / (1000 * 24 * 60 * 60);
		    days = (int) m;
		    days = days + 1;
		}
		return days;
	}
	public int dobilling(Billing bill) {
		q = s.createQuery("from Room where RoomID='" + bill.getRoomid() + "'");
		List<Room> lst = q.list();
		int amt=0;
		for(Room rm : lst) {
			amt = rm.getCostperday();
		}
		int nodays = getnodays(bill.getRoomid());
		bill.setNoofdays(nodays);
		int tot = amt*nodays;
		bill.setBillamt(tot);
		Transaction t = s.beginTransaction();
		s.save(bill);
		t.commit();
		
		t=s.beginTransaction();
		q = s.createQuery("from Room where roomid='"+bill.getRoomid()+"'");
		List<Room> ls = q.list();
		Room r1 = new Room();
		for(Room rm:lst) {
			r1 = rm;
			r1.setStatus(Status.AVAILABLE);
		}
		s.save(r1);
		t.commit();
		return tot;
	}
}
