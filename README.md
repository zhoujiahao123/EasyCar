# EasyCar

停车一体化系统，Android客户端

# Table of Contents

- [EasyCar](#easycar)
- [Table of Contents](#table-of-contents)
  - [1\.概要](#1%E6%A6%82%E8%A6%81)
    - [1\.1 项目介绍](#11-%E9%A1%B9%E7%9B%AE%E4%BB%8B%E7%BB%8D)
    - [1\.2 客户端功能介绍](#12-%E5%AE%A2%E6%88%B7%E7%AB%AF%E5%8A%9F%E8%83%BD%E4%BB%8B%E7%BB%8D)
  - [2\. 功能展示](#2-%E5%8A%9F%E8%83%BD%E5%B1%95%E7%A4%BA)
    - [登陆&amp;注册](#%E7%99%BB%E9%99%86%E6%B3%A8%E5%86%8C)
    - [附近车库&amp;目标车库占用情况&amp;目标车库平面图](#%E9%99%84%E8%BF%91%E8%BD%A6%E5%BA%93%E7%9B%AE%E6%A0%87%E8%BD%A6%E5%BA%93%E5%8D%A0%E7%94%A8%E6%83%85%E5%86%B5%E7%9B%AE%E6%A0%87%E8%BD%A6%E5%BA%93%E5%B9%B3%E9%9D%A2%E5%9B%BE)
    - [导航&amp;用户信息](#%E5%AF%BC%E8%88%AA%E7%94%A8%E6%88%B7%E4%BF%A1%E6%81%AF)
  - [3 开发环境及技术支持](#3-%E5%BC%80%E5%8F%91%E7%8E%AF%E5%A2%83%E5%8F%8A%E6%8A%80%E6%9C%AF%E6%94%AF%E6%8C%81)
    - [3\.1  开发环境及运行平台](#31--%E5%BC%80%E5%8F%91%E7%8E%AF%E5%A2%83%E5%8F%8A%E8%BF%90%E8%A1%8C%E5%B9%B3%E5%8F%B0)
      - [3\.1\.1 开发环境](#311-%E5%BC%80%E5%8F%91%E7%8E%AF%E5%A2%83)
      - [3\.1\.2 运行环境](#312-%E8%BF%90%E8%A1%8C%E7%8E%AF%E5%A2%83)
    - [3\.2 技术支持](#32-%E6%8A%80%E6%9C%AF%E6%94%AF%E6%8C%81)
  - [4\. 最后](#4-%E6%9C%80%E5%90%8E)

## 1.概要

### 1.1 项目介绍

本项目针对现如今私家车辆越来越多但停车难，找车难问题而产生，目的在于让用户快速找到附近停车位，在停车后也能快速找到停车时位置。

- 针对对象：私家车用户
- 项目目的：快速寻找附近车位，停车后寻找到停车点
- 项目组成：硬件端+服务器+Android客户端
- 职责担任：本项目中担任Android开方成员

### 1.2 客户端功能介绍

1. 登陆注册
2. 实时定位
3. 附近车库查询
4. 导航
5. 目标车库占用情况
6. 目标车库平面图
7. 停车地点记录（二维码扫描方式）
8. 离开车库自动扣款

## 2. 功能展示

### 登陆&注册

<img src="https://github.com/zhoujiahao123/EasyCar/blob/master/pic/%E7%99%BB%E9%99%86.jpg" title="登陆" width="250px" ><img src="https://github.com/zhoujiahao123/EasyCar/blob/master/pic/%E6%B3%A8%E5%86%8C.jpg" title="注册" width="250px" >

### 附近车库&目标车库占用情况&目标车库平面图

<img src="https://github.com/zhoujiahao123/EasyCar/blob/master/pic/%E9%99%84%E8%BF%91%E8%BD%A6%E5%BA%93.jpg" title="附近车库" width="250px" ><img src="https://github.com/zhoujiahao123/EasyCar/blob/master/pic/%E7%9B%AE%E6%A0%87%E8%BD%A6%E5%BA%93%E5%8D%A0%E7%94%A8%E6%83%85%E5%86%B5.jpg" title="目标车库占用信息" width="250px" ><img src="https://github.com/zhoujiahao123/EasyCar/blob/master/pic/%E7%9B%AE%E6%A0%87%E8%BD%A6%E5%BA%93%E5%B9%B3%E9%9D%A2%E5%88%86%E5%B8%83%E5%9B%BE.jpg" title="目标车库平面图" width="250px" >

### 导航&用户信息

<img src="https://github.com/zhoujiahao123/EasyCar/blob/master/pic/%E5%AF%BC%E8%88%AA.jpg" title="导航" width="250px" ><img src="https://github.com/zhoujiahao123/EasyCar/blob/master/pic/%E7%94%A8%E6%88%B7%E4%BF%A1%E6%81%AF.jpg" title="用户信息" width="250px" >

## 3 开发环境及技术支持

### 3.1  开发环境及运行平台

#### 3.1.1 开发环境

- Android Studio 2.3.3 
- JDK 1.8,java语言开发

#### 3.1.2 运行环境

- Android平台系列手机
- minSDK>=15

### 3.2 技术支持

1. 界面设计
   - 遵从Google Material Design设计。
2. 网络数据交互
   - OkHttp3：网络请求的优秀开源框架
   - Retrofit2+RxJava：简化网络请求API与主子线程调度
3. 本地数据存储
   - SharedPreference：Android 自带简单本地存储API。
4. 代码解耦
   - MVP设计模式：业务分为3个层次，M-Model，V-View，P-presnter
   - Dagger2：依赖注入框架，减少模块之间的依赖

## 4. 最后

**本项目未曾上线，附近车辆采用模拟硬件完成，所有结果仅供参考。**