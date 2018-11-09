package rxjava;

import rx.Observable;
import rx.Observable.OnSubscribe;
import rx.Subscriber;
import rx.functions.Func1;

/**
 * https://blog.csdn.net/u011033906/article/details/58593574
 * @author  杨赛
 * @since   jdk1.7
 * @version 2018年11月9日 杨赛
 */
public class RxJavaPattern {

	public static void main(String[] args) {
		testObservable();
	}

	@SuppressWarnings("deprecation")
	private static void testObservable() {
		// 1.创建被观察者 Observable
		Observable<String> observable = Observable.create(new OnSubscribe<String>() {

			@Override
			public void call(Subscriber<? super String> subscriber) {

				subscriber.onNext("Hello World！"); // 发送数据
				subscriber.onCompleted();// 最终调用该方法，表示结束
			}

		});

		// 2.创建观察者Observer
		rx.Observer<String> observer = new rx.Observer<String>() {

			@Override
			public void onCompleted() {
				System.out.println("onCompleted");
			}

			@Override
			public void onError(Throwable arg0) {
				System.out.println("onError");
			}

			@Override
			public void onNext(String arg0) {
				System.out.println("onNext = " + arg0);
			}
		};

		// 3.被观察者订阅观察者
		observable
				// 建议在这修改数据
				.map(new Func1<String, String>() {
					// 第一个参数决定 call方法类型，第二个参数决定返回值类型
					@Override
					public String call(String arg0) {
						return arg0 + "汤圆";
					}
				}).subscribe(observer);
	}
}