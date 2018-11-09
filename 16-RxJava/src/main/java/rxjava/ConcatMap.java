/******************************************************************************
* Copyright (C) 2014 ShenZhen ComTop Information Technology Co.,Ltd
* All Rights Reserved.
* 本软件为深圳康拓普开发研制。未经本公司正式书面同意，其他任何个人、团体不得使用、
* 复制、修改或发布本软件.
*****************************************************************************/

package rxjava;

import java.util.Arrays;

import rx.Observable;
import rx.Subscriber;
import rx.functions.Func1;

/**
 * http://wiki.jikexueyuan.com/project/rx-docs/Operators.html
 * @author 杨赛
 * @since jdk1.7
 * @version 2018年11月9日 杨赛
 */
public class ConcatMap {
	public static void main(String[] args) {
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
		
		Observable.from(Arrays.asList("http://www.baidu.com/", "http://www.google.com/", "https://www.bing.com/"))
				.concatMap(new Func1<String, Observable<String>>() {
					@Override
					public Observable<String> call(String s) {
						return createIpObservableMultiThread(s);
					}
				}).retry(2).subscribe(observer);
	}

	// 获取ip
	@SuppressWarnings("deprecation")
	private static Observable<String> createIpObservableMultiThread(final String url) {
		return Observable.create(new Observable.OnSubscribe<String>() {
			@Override
			public void call(Subscriber<? super String> subscriber) {
//				String ip = getIPByUrl(url);
				System.out.println("Emit Data -> " + url);
				if(url == "https://www.bing.com/") {
						subscriber.onError(new RuntimeException("ss"));
				}else {
					subscriber.onNext(url);
					
				}
				subscriber.onCompleted();
			}

//			private String getIPByUrl(String url) {
//				return "110";
//			}
		});
	}
	
	
}
