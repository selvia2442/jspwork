package battle20q;

import battle20q.PagingBean;

public class PagingUtil {
	public static PagingBean setPagingInfo(PagingBean paging){
		
		paging.setStartNum(paging.getTotalCount() - (paging.getNowPage()-1) * paging.getCountPerPage());
		
		
		//���� seq
		paging.setStartseq(paging.getTotalCount()-paging.getStartNum()); 
		//���� seq
		paging.setEndseq(paging.getTotalCount()-paging.getStartNum()+paging.getCountPerPage());
		
		
		return paging;
	}
}
