package quick.pager.pcloud.service;


import quick.pager.pcloud.gen.common.Result;

public interface IDGenService {
    Result get(String key);
    boolean init();
}
