package bot.box.appusage.presenter;

import java.util.List;

import bot.box.appusage.contract.UsageContracts;
import bot.box.appusage.delegate.FetchAppUsageDelegate;
import bot.box.appusage.model.AppData;
import bot.box.appusage.utils.UsageManager;

/**
 * Created by BarryAllen
 *
 * @TheBotBox boxforbot@gmail.com
 */
public class UsagePresenter implements UsageContracts.Presenter {

    private final UsageContracts.View mView;
    long start,end;

    public UsagePresenter(UsageContracts.View view,long start,long end) {
        this.mView = view;
        this.start=start;
        this.end=end;
    }

    @Override
    public void loadUsageData(int duration) {
        new FetchAppUsageDelegate(new FetchAppUsageDelegate.AppUsageCallback() {
            @Override
            public void onPreExecute() {
                mView.showProgress();
            }

            @Override
            public void onAppDataFetch(List<AppData> usageData, long mTotalUsage) {
                UsageManager.getInstance().setAppUsageList(usageData, mTotalUsage);
                mView.getUsageData(usageData, mTotalUsage, duration);
                mView.hideProgress();
            }
        },start,end).executeExecutor(duration);
    }
}
