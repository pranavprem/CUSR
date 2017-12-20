package com.cmpe275.cusr.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Search {
	
	public String searchmix(Character from, Character to, String time) throws ParseException {
		DateFormat df = new SimpleDateFormat("HH:mm");
		DateFormat df2 = new SimpleDateFormat("HHmm");
		String train1,train2 = "",train3;
		Date dep = null;
		
		try{
			dep = df.parse(time);
		}catch(ParseException p) {
			p.printStackTrace();
		}
		Date time2 = dep;
		Date time3 = dep;
		to = Character.toUpperCase(to);
		from = Character.toUpperCase(from);
		Integer t=(int)to-65,f=(int)from-65,hrs =0, min=0, temp=0;
		//finding nearest express station to FROM station
		int i;
		for(i=0;i<26;i+=5) {
			if(i>=f) {
				break;
			}
		}
		if(i!=f) {
			if((i-f)<3){
				train1 = this.search((char)(f+65), (char)(65+i), time);
				System.out.println(train1);
				time2=df2.parse(train1.substring(2, 6));
				temp = i*8-3;
				time2.setHours(time2.getHours()+temp/60);
				time2.setMinutes(time2.getMinutes()+temp%60);
			}else {
				i-=5;
				train1 = this.search((char)(f+65), (char)(65+i), time);
				time2=df2.parse(train1.substring(2, 6));
				temp = (25-i)*8-3;
				time2.setHours(time2.getHours()+temp/60);
				time2.setMinutes(time2.getMinutes()+temp%60);
			}
		}
		else {
			time2=dep;
			train1=null;
		}
		System.out.println(train1);
		System.out.println(df.format(time2));
		
		//finding nearest express station to TO station
		int j;
		for(j=0;j<26;j+=5) {
			if(j>=t) {
				break;
			}
		}
		if(j-t>=3) {
			j-=5;
		}
		time3=time2;
		
		
		
		
		if(j>i) {
			train2+="SB";
			temp=i*5+(i/5)*3;
			
			hrs=time2.getHours()-temp/60;
			min=time2.getMinutes()-(temp%60);
			
			if(min<0) {
				hrs--;
				min=60+min;
			}
			
			
			if(min>0) {
				temp = 60-min;
				time3.setHours(time3.getHours()+(time3.getMinutes()+temp)/60);
				time3.setMinutes((time3.getMinutes()+temp)%60);
				min=0;
				hrs++;
			}
			
			
			temp=(j-i)*5;
			temp+=(((j-i)/5)*3)-3;
			time3.setHours(time3.getHours()+temp/60);
			time3.setMinutes(time3.getMinutes()+temp%60);
			
			
		}else {
			train2+="NB";
			temp=(25-i)*5+((25-i)/5)*3;
			hrs=time2.getHours()-temp/60;
			min=time2.getMinutes()-(temp%60);
			if(min<0) {
				hrs--;
				min=60+min;
			}
			if(min>0) {
				temp = 60-min;
				time3.setHours(time3.getHours()+(time3.getMinutes()+temp)/60);
				time3.setMinutes((time3.getMinutes()+temp)%60);
				min=0;
				hrs++;
			}
			temp=(i-j)*5;
			temp+=(((i-j)/5)*3)-3;
			time3.setHours(time3.getHours()+temp/60);
			time3.setMinutes(time3.getMinutes()+temp%60);
		}
		train2=train2+String.format("%02d%02d", hrs, min);

		
		
		train3=this.search((char)(j+65), to, df.format(time3));
		
		return train1+","+train2+","+train3+"---"+from+","+(char)(i+65)+","+(char)(j+65)+","+to;
		
		
	}
	public String search(Character from, Character to, String time) {
		DateFormat df = new SimpleDateFormat("HH:mm");
		Date dep = null;
		
		try{
			dep = df.parse(time);
		}catch(ParseException p) {
			p.printStackTrace();
		}
		to = Character.toUpperCase(to);
		from = Character.toUpperCase(from);
		
		Integer t=0,f=0,hrs =0, min=0;
		
		//System.out.println(to);
		if(to==from) {
			return null;
		}
		else if ((int)to>(int)from) {
			t = (int)to - 65;
			f = (int)from - 65;
			hrs=dep.getHours()-((8*f)/60);
			min=dep.getMinutes()-((8*f)%60);
			if(min<0) {
				hrs--;
				min=60+min;
			}
			int i;
			for(i=15;i<=60;i+=15) {
				if(i>=min) {
					break;
				}
			}
			if(i==60) {
				hrs++;
				i=0;
			}
			if ((t%5!=0 || f%5!=0) && i==0){
				i+=15;
			}
			
			return("SB"+String.format("%02d%02d", hrs, i));
			
		}else {
			t = 90-(int)to;
			f = 90-(int)from;
			//System.out.println(8*f);
			hrs=dep.getHours()-((8*f)/60);
			min=dep.getMinutes()-((8*f)%60);
			
			if(min<0) {
				hrs--;
				min=60+min;
			}
			
			
			int i;
			for(i=15;i<=60;i+=15) {
				if(i>=min) {
					break;
				}
			}
			if(i==60) {
				hrs++;
				i=0;
			}
			if ((t%5!=0 || f%5!=0) && i==0){
				i+=15;
			}
			
			
			return("NB"+String.format("%02d%02d", hrs, i));
		}
		
	}
	
	public String findtime(String train, Character station) {
		DateFormat df = new SimpleDateFormat("HHmm");
		DateFormat df2 = new SimpleDateFormat("HH:mm");
		Date dep = null;
		int temp;
		
		try{
			dep = df.parse(train.substring(2, 6));
		}catch(ParseException p) {
			p.printStackTrace();
		}
		
		if(dep.getMinutes()==0) {
			temp = ((int)station-65) * 5 + (((int)station-65)/5)*3 - 3;
			System.out.println(temp);
			dep.setMinutes(dep.getMinutes()+temp);
		}else {
			temp = ((int)station-65) * 8 - 3;
			dep.setMinutes(dep.getMinutes()+temp);
		}
		
	
		
		return df2.format(dep);
	}

}
