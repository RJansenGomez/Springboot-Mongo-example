package org.rjansen.common.repository;

public class BaseFilter {
    protected BaseFilter() {

    }

    protected Integer page;
    protected Integer pageSize;

    /**
     * Default page its 0
     * @return the settled value if not value, 0
     */
    public int getPage() {
        if (null != page)
            return page;
        return 0;
    }

    public void setPage(int page) {
        this.page = page;
    }

    /**
     * Default pageSize its 10
     * @return the settled value if not value, 10
     */
    public int getPageSize() {
        if (null != pageSize)
            return pageSize;
        return 10;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }
}
