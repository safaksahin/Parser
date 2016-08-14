package tr.com.safak.sahin;

import java.util.ArrayList;
import java.util.Collections;

public class TestBed {

	/**
	 * @param args
	 */
	public static void main(String[] args) {

		String voc = "enstantane";//"Herşey vatan için!";//"matrak insanlar";//"elektronik eşya sektörü çok heyecan verici gelişmelere sahne oluyor.";//"Verme dünyaları alsanda, bu cennet vatanı!"; //"Trakya'dan Edirne'ye nasıl gidebilirim.";
		ArrayList<String> syllables = parser(voc);
		Collections.reverse(syllables);
		for (String string : syllables) {
			System.out.println("'"+ string + "'");
		}

	}

	public static ArrayList<String> parser(String voc) {
		voc = voc.replaceAll(" ","");//boşlukları çıkartıyoruz.
		int len = voc.length();
		ArrayList<String> syllable = new ArrayList<String>();
		if (len > 0) {
			char lastChar = voc.charAt(len - 1);
			if(isPunctuationMark(lastChar)){
				syllable.add(new StringBuilder().append(lastChar).toString());
				syllable.addAll(parser(deleteSyllable(voc, 1)));
			}
			else if (isVowel(lastChar) && len > 1) {// Son harf sesli ise son iki harf hecedir.
				char secondLastChar = voc.charAt(len - 2);
				if(isPunctuationMark(secondLastChar)){
					syllable.add(new StringBuilder().append(lastChar).toString());
					syllable.addAll(parser(deleteSyllable(voc, 1)));
				}
				else if (isVowel(secondLastChar)) {
					syllable.add(new StringBuilder().append(lastChar).toString());
					syllable.addAll(parser(deleteSyllable(voc, 1)));
				}
				else {
					syllable.add(new StringBuilder().append(secondLastChar).append(lastChar).toString());
					syllable.addAll(parser(deleteSyllable(voc, 2)));
				}
			}
			else if (len > 2) {// son harf sessiz harf ise son 3 harf hecedir.
				char secondLastChar = voc.charAt(len - 2);
				char thirdLastChar = voc.charAt(len - 3);
				if(isPunctuationMark(secondLastChar)){
					syllable.add(new StringBuilder().append(lastChar).toString());
					syllable.addAll(parser(deleteSyllable(voc, 1)));
				}
				else if(isPunctuationMark(thirdLastChar)){
					syllable.add(new StringBuilder().append(secondLastChar).append(lastChar).toString());
					syllable.addAll(parser(deleteSyllable(voc, 2)));
				}
				else if(!isVowel(secondLastChar))//kask, pers gibi heceler.
				{
					char fourthLastChar = len>=4 ? voc.charAt(len-4) : '\0';
					int index = len>=4 ? 4 : 3;
					syllable.add(new StringBuilder().append(fourthLastChar).append(thirdLastChar).append(secondLastChar).append(lastChar).toString());
					syllable.addAll(parser(deleteSyllable(voc, index)));
				}
				else if( len >= 4 && isSpecialSyllable(voc, len))//trak gibi heceler.
				{
					char fourthLastChar = len>=4 ? voc.charAt(len-4) : null;
					syllable.add(new StringBuilder().append(fourthLastChar).append(thirdLastChar).append(secondLastChar).append(lastChar).toString());
					syllable.addAll(parser(deleteSyllable(voc, 4)));
				}
				else if(isVowel(thirdLastChar))
				{
					syllable.add(new StringBuilder().append(secondLastChar).append(lastChar).toString());
					syllable.addAll(parser(deleteSyllable(voc, 2)));
				}
				else {
					syllable.add(new StringBuilder().append(thirdLastChar).append(secondLastChar).append(lastChar).toString());
					syllable.addAll(parser(deleteSyllable(voc, 3)));
				}
			}
			else if (len>1 && len<3)
			{
				syllable.add(new StringBuilder().append(voc.charAt(0)).append(voc.charAt(1)).toString());
				return syllable;
			}
			else if (len == 1) {// Tek harf kaldıysa
				syllable.add(new StringBuilder().append(voc.charAt(0)).toString());
				return syllable;
			}

		}
		else {
			return syllable;
		}
		return syllable;
	}

	public static boolean isVowel(char x) {//sesli harf mi?
		String vowels = "aeıioöuüAEIİOÖUÜ";
		if (vowels.indexOf(x) >= 0)
			return true;
		else
			return false;
	}
	public static boolean isPunctuationMark(char x){
		String punctuationMarks = ".,!?'-";
		if(punctuationMarks.indexOf(x) >= 0)
			return true;
		else
			return false;
	}

	public static String deleteSyllable(String voc, int index) {//heceyi sil.
		return new StringBuilder(voc).delete(voc.length() - index, voc.length()).toString();
	}
	public static boolean isSpecialSyllable(String voc, int length){//ilk iki harfi veya son iki harfi sessiz olan kelimeler
		int count = 0;
		
		for (int i = 0; i < length; i++) {
			count = isVowel(voc.charAt(i)) ? count+1 : count;
		}
		if (length > 4) {
			if (length == 5 || isVowel(voc.charAt(length - 5))) {
				return false;
			}
			else if (!isVowel(voc.charAt(length - 5)) && isVowel(voc.charAt(length - 4))) {
				return false;
			}
			else
			{
				return true;
			}
		}
		else if (isPunctuationMark(voc.charAt(length-4)))
		{
			return false;
		}
		else
		{
			return true;
		}
	}
}
