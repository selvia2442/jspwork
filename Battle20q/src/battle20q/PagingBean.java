package battle20q;

public class PagingBean {
	/*
	 * 현재 페이지
	 * */
	private int nowPage;
	/*
	 * 현재 첫번째 row의 넘버
	 * */
	private int startNum;
	/*
	 * 게시판 총 페이지
	 * */
	private int totalCount;
	/*
	 * 1 페이지당 보여줄 리스트 갯수
	 * */
	private int countPerPage;
	/*
	 * paging page 숫자의 블록 카운트
	 * */
	private int blockCount;
	/*
	 * 검색 컬럼
	 * */
	private String searchColumn;
	/*
	 * 검색어
	 * */
	private int startseq;
	/*
	 * 시작
	 * */
	private int endseq;
	/*
	 * 종료
	 * */
	private String searchWord;
	
	
	
	
	public int getStartseq() {
		return startseq;
	}
	public void setStartseq(int i) {
		this.startseq = i;
	}
	public int getEndseq() {
		return endseq;
	}
	public void setEndseq(int i) {
		this.endseq = i;
	}
	public int getNowPage() {
		return nowPage;
	}
	public void setNowPage(int nowPage) {
		this.nowPage = nowPage;
	}
	public int getTotalCount() {
		return totalCount;
	}
	public void setTotalCount(int totalCount) {
		this.totalCount = totalCount;
	}
	public int getCountPerPage() {
		return countPerPage;
	}
	public void setCountPerPage(int countPerPage) {
		this.countPerPage = countPerPage;
	}
	public int getBlockCount() {
		return blockCount;
	}
	public void setBlockCount(int blockCount) {
		this.blockCount = blockCount;
	}
	public String getSearchColumn() {
		return searchColumn;
	}
	public void setSearchColumn(String searchColumn) {
		this.searchColumn = searchColumn;
	}
	public String getSearchWord() {
		return searchWord;
	}
	public void setSearchWord(String searchWord) {
		this.searchWord = searchWord;
	}
	public int getStartNum() {
		return startNum;
	}
	public void setStartNum(int startNum) {
		this.startNum = startNum;
	}
	
}
