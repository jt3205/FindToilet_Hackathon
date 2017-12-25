package evaluation;

public class EvaluationVO {
	private int no;
	private String comment;
	private int star;
	private String date;

	public int getNo() {
		return no;
	}

	public void setNo(int no) {
		this.no = no;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public double getStar() {
		return star;
	}

	public void setStar(int star) {
		this.star = star;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

}
