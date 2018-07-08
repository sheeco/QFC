package pers.sunyunmiao.qfc.contentcounter.po;

import com.google.common.base.MoreObjects;

import java.net.MalformedURLException;
import java.util.Date;

public class Record {

	private String url = "";
	//	private URL url;
	private String title = "无标题";
	private Integer countCharacter;
	private Integer countChinese;
	private Integer countEnglish;
	private Integer countPunctuation;
//	//非空校验
//	@NotNull(message = "{items.updateTime.isNUll}")
	private Date updateTime;


	public Record(String url, String title, Integer countCharacter, Integer countChinese,
	              Integer countEnglish, Integer countPunctuation) {

		this.setUrl(url);
		if(title != null &&
				!title.equals("")) {

			this.setTitle(title);
		}
		this.setCountCharacter(countCharacter);
		this.setCountChinese(countChinese);
		this.setCountEnglish(countEnglish);
		this.setCountPunctuation(countPunctuation);
		this.setUpdateTime(new Date());
	}

	public Record() throws MalformedURLException {

		this("http://", "", 0, 0, 0, 0);
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public Integer getCountCharacter() {
		return countCharacter;
	}

	public void setCountCharacter(Integer countCharacter) {
		this.countCharacter = countCharacter;
	}

	public Integer getCountChinese() {
		return countChinese;
	}

	public void setCountChinese(Integer countChinese) {
		this.countChinese = countChinese;
	}

	public Integer getCountEnglish() {
		return countEnglish;
	}

	public void setCountEnglish(Integer countEnglish) {
		this.countEnglish = countEnglish;
	}

	public Integer getCountPunctuation() {
		return countPunctuation;
	}

	public void setCountPunctuation(Integer countPunctuation) {
		this.countPunctuation = countPunctuation;
	}

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

	@Override
	public String toString() {
		return MoreObjects.toStringHelper(this)
				.add("url", url)
				.add("title", title)
				.add("countCharacter", countCharacter)
				.add("countChinese", countChinese)
				.add("countEnglish", countEnglish)
				.add("countPunctuation", countPunctuation)
				.add("updateTime", updateTime)
				.toString();
	}
}
