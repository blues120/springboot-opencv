package com.example.filedemo;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.opencv.core.Mat;
import org.opencv.imgcodecs.Imgcodecs;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;

import static org.apache.tomcat.jni.Stdlib.memset;

@RunWith(SpringRunner.class)
@SpringBootTest
public class FileDemoApplicationTests {
	//扩展欧几里得算法
	void gcd(ll a, ll b, ll &d, ll &x, ll &y)
	{
		if (b == 0) {
			d = a;
			x = 1, y = 0;
		}
		else {//else不能省略
			gcd(b, a%b, d, y, x);
			y -= (a / b)*x;
		}
	}

	//中国剩余定理
	ll em[15], ea[15];
	ll China(int n, ll *m, ll *a)
	{
		ll M = 1, d, y, x = 0;
		for (int i = 0; i < n; i++) M *= em[i];
		for (int i = 0; i < n; i++) {
			ll w = M / em[i];
			gcd(em[i], w, d, d, y);
			x = (x + y * w*a[i]) % M;
		}
		return (x + M) % M;
	}
/*
*participateNum  参与秘密分配的人数,对应参数Num
*bWeight 存放输入的秘密份额秘密份额,对应参数b[]
*Threshold 阈值， 对应参数ω
* */
	@Test
	public void contextLoads(Integer participateNum,int bWeight[],int Threshold) {
		////基本阶段，产生mignogge序列，生成秘密份额
// 		参与人数
		int num=0;

		int  ω, α, β;
		//存放生成的素数
		int a[] = new int[100];
		//存放输入的秘密份额秘密份额
		int b[] = new int[10];
		//存放生成的权限值
		int d[] = new int[]{0,0,0,0,0,0,0,0,0,0};
		//生成α时作为中间过渡值
		int e[] = new int[100];
		for (int i = 0; i <100 ; i++) {
			e[i]=0;
		}
		//生成β时作为中间过渡值
		int f[] = new int[100];
		for (int j = 0; j <100 ; j++) {
			f[j]=0;
		}




		int k = -0;
		for (int i = 0; i < num; i++)
		{
			k = k + b[i];
		}
		int ai, an = 2;
		int j = 0;
		for (ai = 11; j < k; ai++)// 随机素数生成
		{
			while (ai >= an)
			{
				if (ai%an != 0)
					an++;
				else break;
			}
			if (ai == an)
			{
				a[j] = ai;
				j++;
			}
			an = 2;
		}
		//cout << " " << endl;
		//for (j = 0; j < k; j++)
		//printf("%d ", a[j]);//检验素数是否被存入数组（可删除）
		int l = 0;
		for ( l = 0, j = 0; (l < num)&&(j < k); l++)//将素数按权重存入数组
		{
			if (b[l] == 1)
			{
				d[l] = a[j];
				j++;
			}
			else if (b[l] == 2)
			{
				d[l] = a[j] * a[j + 1];
				j = j + 2;
			}
			else if (b[l] == 3)
			{
				d[l] = a[j] * a[j + 1] * a[j + 2];
				j = j + 3;
			}
			else if (b[l] == 4)
			{
				d[l] = a[j] * a[j + 1] * a[j + 2] * a[j + 3];
				j = j + 4;
			}
			else
			{
				d[l] = a[j] * a[j + 1] * a[j + 2] * a[j + 3] * a[j + 4];
				j = j + 5;
			}
		}



		////秘密图像共享预备阶段，产生α 和 β
		int p, q, m;
		int ap;
		for (ap = 0, p = 0, l = 0; l < num; l++)
		{
			if (b[l] >= ω)
			{
				e[p] = d[l];
				p++;
				ap++;
			}
			else for (j = l + 1; j < num; j++)
			{
				if (b[l] + b[j] >= ω)
				{
					e[p] = d[l] * d[j];
					p++;
					ap++;
				}
				else for (q = j + 1; q < num; q++)
				{
					if (b[l] + b[j] + b[q] >= ω)
					{
						e[p] = d[l] * d[j] * d[q];
						p++;
						ap++;
					}
					else for (m = q + 1; m < num; m++)
					{
						if (b[l] + b[j] + b[q] + b[m] >= ω)
						{
							e[p] = d[l] * d[j] * d[q] * d[m];
							p++;
							ap++;
						}
					}

				}

			}
		}
		//求數組最大值
		α=e[0];
		for(int i=0; i<ap - 1;i++){
			if(e[i]>α)
				α=e[i];
		}
//		α = *min_element(e, e + ap - 1); //找出α的值

		int bp;
		for (bp = 0, p = 0, l = 0; l < num; l++)
		{
			if (b[l] < ω)
			{
				f[p] = d[l];
				p++;
				bp++;
			}
			for (j = l + 1; j < num; j++)
			{
				if (b[l] + b[j] < ω)
				{
					f[p] = d[l] * d[j];
					p++;
					bp++;
				}
				else
				{
					for (q = j + 1; q < num; q++)
					{
						if (b[l] + b[j] + b[q] < ω)
						{
							f[p] = d[l] * d[j] * d[q];
							p++;
							bp++;
						}
						else
						{
							for (m = q + 1; m < num; m++)
							{
								if (b[l] + b[j] + b[q] + b[m] < ω)
								{
									f[p] = d[l] * d[j] * d[q] * d[m];
									p++;
									bp++;
								}
							}
						}
					}
				}
			}
		}

		//找出β的值
		β=f[0];
		for(int i=0; i<bp - 1;i++){
			if(f[i]<β)
				β=f[i];
		}
//		β = *max_element(f, f + bp - 1);

		//输出B个秘密图像二进制值被视为一段
		int B;
//		B = floor(log2(α - β));
		B = (int)Math.floor(Math.log(α - β));

		Mat img1 = Imgcodecs.imread("E:\\pictureSecret\\test.png",Imgcodecs.CV_LOAD_IMAGE_GRAYSCALE);
//		Mat img1 = imread("F:/存放素材/秘密图像和载体/11.png", CV_8U);//读取秘密图像
		if (img1.empty())
		{
//			cout << "图片读取错误，请检查" << endl;
//			exit(1);
		}
		int rowNumber1 = img1.rows();
		int cowNumber1 = img1.cols();

		int s1[][] = new int[200][200];
		for (int r = 0; r < img1.rows(); r++)//将秘密图像灰度值按像素存入二维数组
		{
			for (int c = 0; c < img1.cols(); c++)
			{
//				todo
//				s1[r][c] = (int)img1.at<uchar>(r, c);
				s1[r][c] = (int)img1.get(img1.rows(),img1.cols())[0];
			}
		}

		int ax = 0;
		int ag[] = new int[320010];// 200*200*8/B，向上取整，再*B
		Arrays.fill(ag, 0);
		for (int r = 0; r < img1.rows(); r++)//十进制灰度值转八位二进制并且存入新数组ag[ax]中
		{
			for (int c = 0; c < img1.cols(); c++)
			{
				int bi;
				int bj = 0;
				int i[] = new int[8];
				Arrays.fill(i,0);
				bi = s1[r][c];
//				todo while (bi) repair bi!=0
				while (bi!=0)
				{
					i[bj] = bi % 2;
					bi /= 2;
					bj++;
				}
				for (bi = 7; bi >= 0; bi--, ax++)
				{
					ag[ax] = i[bi];
				}
			}
		}

		int cb = 0;
		int ct;
//		int si[21334] = { 0 };//200*200*8/B,向上取整
		int si[] = new int[21334];// 200*200*8/B，向上取整，再*B
		Arrays.fill(si, 0);
		int cxt = B - 1;
		for (ct = 0; ct < 21334; ct++)
		{
			for (ax = cb; ax < cb + B; ax++)
			{
				si[ct] = si[ct] + ag[ax] * (int)Math.pow(2, cxt);
				cxt--;
			}
			cb = cb + B;
			cxt = B - 1;
		}
		//将15个数值组成一组并输出


		////秘密图像共享阶段
		int bi;
//		int sii[21334] = { 0 };//200*200*8/B,向上取整
		int sii[] = new int[21334];// 200*200*8/B，向上取整，再*B
		Arrays.fill(sii, 0);
		for (bi = 0; bi < 21334; bi++)
		{
			sii[bi] = si[bi] + (int)Math.pow(2, B) * (int)Math.floor((α - si[bi]) / (int)Math.pow(2, B));
		}

		int xk[][] = new int[21334][5];//200*200*8/B,向上取整，5个参与者
		Arrays.fill(xk, 0);
		int bn = 0;
		for (bi = 0; bi < 21334; bi++)
		{
			for (l = 0; l < num; l++)
			{
				xk[bi][l] = sii[bi] % d[l];
			}
		}

		int xl;
//		xl = ceil((log(*max_element(d, d + num - 1) - 1) / log(5)));//计算生成的秘密份额由几位5进制数组成
//		取最大值max
		int tempD=d[0];
		for(int i=0; i<num - 1;i++){
			if(d[i]>tempD)
				tempD=d[i];
		}
		xl = (int)Math.ceil((Math.log(tempD - 1) / Math.log(5)));

		int xkj[][][] = new int[5][21334][5];//5（xl）位5进制数；共有200*200*8/B,向上取整；5个参与者
		Arrays.fill(xkj,0);
		for (bi = 0; bi < 21334; bi++)//将xk变化为以5为基的形式xkj，并将变化以后的l位5进制存入新的三维数组中
		{
			for (l = 0; l < num; l++)
			{
				int ba[] = new int[5];
				Arrays.fill(ba,0);
				int bj = 0;
				int bb = 0;
				int t;
				t = xk[bi][l];
//				todo
				while (t!=0)
				{
					ba[bj] = t % 5;
					t /= 5;
					bj++;
				}
				for (bb = 0, bj = 4; bj >= 0; bb++, bj--)//bj=xl-1 位
				{
					xkj[bb][bi][l] = ba[bj];
				}
			}
		}



		////秘密图像嵌入阶段
		Mat img2 = Imgcodecs.imread("E:\\pictureSecret\\test.png",Imgcodecs.CV_LOAD_IMAGE_GRAYSCALE);;//读取宿主图像
		if (img2.empty())
		{
//			cout << "图片读取错误，请检查" << endl;
//			exit(1);
		}
		int rowNumber2 = img2.rows();
		int cowNumber2 = img2.cols();
		int s2[][] = new int[400][400];
		Arrays.fill(s2,0);
		for (int r = 0; r < rowNumber2; r++)//将宿主图像灰度值按像素存入二维数组
		{
			for (int c = 0; c < cowNumber2; c++)
			{
//				todo
				s2[r][c] = (int)img2.get(img2.rows(),img2.cols())[0];
			}
		}

		int pj[][][] = new int[5][400][400];//5个参与者，宿主图像大小400*400
		Arrays.fill(pj,0);
		for (int r = 0; r < rowNumber2; r++)//先做出宿主图像的若干份待处理的宿主图像
		{
			for (int c = 0; c < cowNumber2; c++)
			{
				for (l = 0; l < num; l++)
				{
					pj[l][r][c] = s2[r][c];
				}
			}
		}

		int pij[][][] = new int[5][400][400];
		Arrays.fill(pij,0);
		for (int r = 0; r < rowNumber2; r++)//先做出宿主图像的若干份待处理的宿主图像
		{
			for (int c = 0; c < cowNumber2; c++)
			{
				for (l = 0; l < num; l++)
				{
					pij[l][r][c] = pj[l][r][c];
				}
			}
		}


		int dj[][][] = new int[5][400][400];
		Arrays.fill(dj,0);
		for (int r = 0; r < rowNumber2; r++)//先做出宿主图像的若干份待处理的宿主图像
		{
			for (int c = 0; c < cowNumber2; c++)
			{
				for (l = 0; l < num; l++)
				{
					dj[l][r][c] = pj[l][r][c] % 5;
				}
			}
		}



		int da = 0;
		bi = 0;
		l = 0;
		for (int r = 0; r < rowNumber2; r++)//将秘密份额嵌入宿主图像的像素值中，生成与加解密参与份数相同的秘密图片
		{
			if (bi > 21334)
			{
				break;
			}
			for (int c = 0; c < cowNumber2; c++)
			{
				if (bi > 21334)
				{
					break;
				}
				for (da = 0; da < num; da++)
				{
//					todo
					int temp=(dj[da][r][c] - xkj[l][bi][da]);
					if ((2 <temp)&&( temp< 5))
					{
						pij[da][r][c] = pj[da][r][c] - dj[da][r][c] + 5 + xkj[l][bi][da];
					}
					else if ((-2 <temp)&&( temp< 2))
					{
						pij[da][r][c] = pj[da][r][c] - dj[da][r][c] + xkj[l][bi][da];
					}
					else if ((-5 <temp)&&( temp< -2))
					{
						pij[da][r][c] = pj[da][r][c] - dj[da][r][c] - 5 + xkj[l][bi][da];
					}
				}
				l++;
				if (l >= xl)//bb若等于五进制数位数，清零，进入下一秘密数组
				{
					l = 0;
					bi++;
				}

			}
		}


		for (int r = 0; r < rowNumber2; r++)//若发生溢出（灰度值大于255），则减5处理
		{
			for (int c = 0; c < cowNumber2; c++)
			{
				for (da = 0; da < num; da++)
				{
					if (pij[da][r][c] > 255)
					{
						pij[da][r][c] = pij[da][r][c] - 5;
					}
					else if (pij[da][r][c] < 0)
					{
						pij[da][r][c] = pij[da][r][c] + 5;
					}

				}
			}
		}


		int r, c;
		Mat M1= new Mat(400,400,Imgcodecs.CV_LOAD_IMAGE_GRAYSCALE);
//		Mat M1(400, 400, CV_8UC1);//创建一个高400,宽400的灰度图的Mat对象
		namedWindow("Test1");     //创建一个名为Test窗口
		name
		for (r = 0; r < M1.rows; r++)        //遍历每一行每一列并设置其像素值
		{
			for (c = 0; c < M1.cols; c++)
			{
				M1.at<uchar>(r, c) = pij[0][r][c];
			}
		}
		imshow("Test1", M1);   //窗口中显示图像
		imwrite("F:/存放素材/生成的秘密份额/1.png", M1);    //保存生成的图片
		cvDestroyWindow("Test1");

		Mat M2(400, 400, CV_8UC1);//创建一个高400,宽400的灰度图的Mat对象
		namedWindow("Test2");     //创建一个名为Test窗口
		for (r = 0; r < M2.rows; r++)        //遍历每一行每一列并设置其像素值
		{
			for (c = 0; c < M2.cols; c++)
			{
				M2.at<uchar>(r, c) = pij[1][r][c];
			}
		}
		imshow("Test2", M2);   //窗口中显示图像
		imwrite("F:/存放素材/生成的秘密份额/2.png", M2);    //保存生成的图片
		cvDestroyWindow("Test2");

		Mat M3(400, 400, CV_8UC1);//创建一个高400,宽400的灰度图的Mat对象
		namedWindow("Test3");     //创建一个名为Test窗口
		for (r = 0; r < M3.rows; r++)        //遍历每一行每一列并设置其像素值
		{
			for (c = 0; c < M3.cols; c++)
			{
				M3.at<uchar>(r, c) = pij[2][r][c];
			}
		}
		imshow("Test3", M3);   //窗口中显示图像
		imwrite("F:/存放素材/生成的秘密份额/3.png", M3);    //保存生成的图片
		cvDestroyWindow("Test3");

		Mat M4(400, 400, CV_8UC1);//创建一个高400,宽400的灰度图的Mat对象
		namedWindow("Test4");     //创建一个名为Test窗口
		for (r = 0; r < M4.rows; r++)        //遍历每一行每一列并设置其像素值
		{
			for (c = 0; c < M4.cols; c++)
			{
				M4.at<uchar>(r, c) = pij[3][r][c];
			}
		}
		imshow("Test4", M4);   //窗口中显示图像
		imwrite("F:/存放素材/生成的秘密份额/4.png", M4);    //保存生成的图片
		cvDestroyWindow("Test4");

		Mat M5(400, 400, CV_8UC1);//创建一个高400,宽400的灰度图的Mat对象
		namedWindow("Test5");     //创建一个名为Test窗口
		for (r = 0; r < M5.rows; r++)        //遍历每一行每一列并设置其像素值
		{
			for (c = 0; c < M5.cols; c++)
			{
				M5.at<uchar>(r, c) = pij[4][r][c];
			}
		}
		imshow("Test5", M5);   //窗口中显示图像
		imwrite("F:/存放素材/生成的秘密份额/5.png", M5);    //保存生成的图片
		cvDestroyWindow("Test5");
		cout << " " << endl;


		////秘密图像还原程序
		int dr;
		int dc;
		int dp[5] = { 0 };
		char stre[100] = "请输入参加图像还原的人数: ";
		cout << stre << endl;
		cin >> dr;
		char strf[100] = "请输入参加图像还原的的具体成员，编号从小到大: ";
		cout << strf << endl;
		for (dc = 0; dc < dr; dc++)
			cin >> dp[dc];
		int pqj[5][400][400] = { 0 };//最多5个秘密还原参与者，秘密份额大小为400*400
		Mat M11 = imread("F:/存放素材/生成的秘密份额/1.png", CV_8UC1);//读取第一张秘密份额图片
		if (M11.empty())
		{
			cout << "图片读取错误，请检查" << endl;
			exit(1);
		}
		for (r = 0; r < M11.rows; r++)
		{
			for (c = 0; c < M11.cols; c++)
			{
				pqj[0][r][c] = (int)M11.at<uchar>(r, c);//存入第一个参与者灰度值数组
			}
		}

		Mat M12 = imread("F:/存放素材/生成的秘密份额/2.png", CV_8UC1);//读取第二张秘密份额图片
		if (M12.empty())
		{
			cout << "图片读取错误，请检查" << endl;
			exit(1);
		}
		for (r = 0; r < M12.rows; r++)
		{
			for (c = 0; c < M12.cols; c++)
			{
				pqj[1][r][c] = (int)M12.at<uchar>(r, c);//存入第一个参与者灰度值数组
			}
		}


		Mat M13 = imread("F:/存放素材/生成的秘密份额/3.png", CV_8UC1);//读取第三张秘密份额图片
		if (M13.empty())
		{
			cout << "图片读取错误，请检查" << endl;
			exit(1);
		}
		for (r = 0; r < M13.rows; r++)
		{
			for (c = 0; c < M13.cols; c++)
			{
				pqj[2][r][c] = (int)M13.at<uchar>(r, c);//存入第一个参与者灰度值数组
			}
		}


		Mat M14 = imread("F:/存放素材/生成的秘密份额/4.png", CV_8UC1);//读取第四张秘密份额图片
		if (M14.empty())
		{
			cout << "图片读取错误，请检查" << endl;
			exit(1);
		}
		for (r = 0; r < M14.rows; r++)
		{
			for (c = 0; c < M14.cols; c++)
			{
				pqj[3][r][c] = (int)M14.at<uchar>(r, c);//存入第一个参与者灰度值数组
			}
		}


		Mat M15 = imread("F:/存放素材/生成的秘密份额/5.png", CV_8UC1);//读取第五张秘密份额图片
		if (M15.empty())
		{
			cout << "图片读取错误，请检查" << endl;
			exit(1);
		}
		for (r = 0; r < M15.rows; r++)
		{
			for (c = 0; c < M15.cols; c++)
			{
				pqj[4][r][c] = (int)M15.at<uchar>(r, c);//存入第一个参与者灰度值数组
			}
		}

		int dd;
		int pxj[5][400][400] = { 0 };//保存所有参与者提供的秘密份额的新三维数组
		for (dd = 0; dd < dr; dd++)//提取出指定参与者拥有的份额图像灰度值存入新三维数组
		{
			for (r = 0; r < 400; r++)
			{
				for (c = 0; c < 400; c++)
				{
					pxj[dd][r][c] = pqj[dp[dd] - 1][r][c] % 5;
				}
			}
		}

		int das;
		int dxk[5][160000] = { 0 };//存取最多5个参与者，400*400大小的秘密数据
		for (dd = 0; dd < dr; dd++)
		{
			das = 0;
			for (r = 0; r < 400; r++)
			{
				for (c = 0; c < 400; c++)
				{
					dxk[dd][das] = dxk[dd][das] + pxj[dd][r][c];//存取最多5个参与者，400*400大小的图像数据
					das++;
				}
			}
		}

		int dm = 0;
		int dxt = 4;
		int dyt = 0;
		int dxp[5][21334] = { 0 };//存放最多dr个参与者，共有200*200*8/B,向上取整
		for (dd = 0; dd < dr; dd++)//dr个秘密还原参与者
		{
			dm = 0;//计数器
			dxt = xl - 1;
			for (dyt = 0; dyt < 21334; dyt++)
			{

				for (das = dm; das < dm + xl; das++)//上限为dm+xl个
				{
					dxp[dd][dyt] = dxp[dd][dyt] + dxk[dd][das] * pow(5, dxt);
					dxt--;
				}
				dm = dm + xl;//dm往后移动xl个
				dxt = xl - 1;//xl-1
			}
		}

		int sri[21334] = { 0 };//200*200*8/B,向上取整
		int ei = 0;
		int ep = pow(2, B);
		int en = dr;
		for (ei = 0, dyt = 0; ei < 21334; dyt++, ei++)
		{
			for (int i = 0, dd = 0; i < dr; i++, dd++)
			{
				em[i] = d[dp[i] - 1];//秘密还原参与者的份额大小
				ea[i] = dxp[dd][dyt];//产生的十进制秘密数据
			}
			sri[ei] = China(en, em, ea) % ep;//利用中国剩余定理计算完成以后对2^B次方取余
		}

		int eg[320010] = { 0 };//200*200*8/B,向上取整后再*B
		int ex = 0;
		for (ei = 0; ei < 21334; ei++)//200*200*8/B,向上取整
		{
			int fi, fj = 0;
			int fe[15] = { 0 };//B位数组，暂存sri[ei]转换成B位2进制的值
			fi = sri[ei];
			while (fi)
			{
				fe[fj] = fi % 2;
				fi /= 2;
				fj++;
			}
			for (fj = B - 1; fj >= 0; fj--, ex++)//B为2进制数，第一位权值为B-1
			{
				eg[ex] = fe[fj];
			}
		}

		int eb = 0;
		int et;
		int seg[40000] = { 0 };//200*200个还原出的秘密图像像素灰度值
		for (et = 0; et < 40000; et++)
		{
			int ext = 7;
			for (ex = eb; ex < eb + 8; ex++)
			{
				seg[et] = seg[et] + eg[ex] * pow(2, ext);//将生成的B位2进制数转十进制后，全部变回2进制，8个分为一组，200*200个灰度值
				ext--;
			}
			eb = eb + 8;
		}

		int sp[200][200] = { 0 };//与秘密图像大小对应的二维数组
		int bl, bm;
		et = 0;
		for (bl = 0; bl < 200; bl++)
		{
			for (bm = 0; bm < 200; bm++)
			{
				sp[bl][bm] = seg[et];//将200*200个灰度值按顺序存入二维数组中
				et++;
			}
		}

		Mat M111(200, 200, CV_8UC1);//创建一个高200，宽200的灰度图的Mat对象
		namedWindow("Test111");     //创建一个名为Test窗口
		for (int bl = 0; bl < M111.rows; bl++)        //遍历每一行每一列并设置其像素值
		{
			for (int bm = 0; bm < M111.cols; bm++)
			{
				M111.at<uchar>(bl, bm) = sp[bl][bm];
			}
		}
		imshow("Test111", M111);   //窗口中显示图像
		imwrite("F:/存放素材/还原出的秘密图像/111.png", M111);    //保存生成的图片
		cvDestroyWindow("Test111");
		getchar();



		return 0;//结束
	}

}
