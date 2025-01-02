<p>
  
<h3 align="center"> MyVersion - AI 커버를 활용한 맞춤형 보컬 트레이닝 </h3>
  
<p align="center">
  
  <img src="https://github.com/user-attachments/assets/8363e721-18da-46d0-8833-37746f3c8148"/>

</p>

<h2>🙇 프로젝트 소개</h2>

<p> 
기존 노래 연습 방식에서는 훈련자가 원곡과의 유사성을 높이기 위해 모창을 반복하는 것이 일반적입니다. 
그러나 이 방식은 원곡 가수와 훈련자 간의 목소리 톤, 고유 음역대와 같은 특징적 차이를 간과합니다. 
또한, 대부분의 일반인은 자신이 부른 노래와 원곡의 유사성을 객관적으로 평가하기 어렵다는 한계가 있습니다. 

마이버던 프로젝트는 VC (Voice- Conversion) 기술을 활용하여 이러한 문제점을 해결할 수 있는 접근법을 
제안하고, 효과적으로 노래를 연습할 수 있는 방법을 제공합니다.
</p>

<br>

<h2>📱 주요 애플리케이션 기능 및 화면</h2>

| 커버 생성 | 평가 생성 | 평가 확인 |
|:-------:|:-------:|:-------:|
| <video src="https://github.com/user-attachments/assets/e6f079a4-309d-48bb-9711-5807a5e3423e" width=300/> | <video src="https://github.com/user-attachments/assets/cc86dd74-f012-46e3-924f-43984e40888f" width=300/> | <video src="https://github.com/user-attachments/assets/5acf53c0-ee6a-45fa-844f-4acf4142f82a" width=300/>

### 커버 생성
  - `MediaRecorder`와 `Coroutine`을 활용한 녹음 기능 제공
  - `ScopedStorage`를 활용한 녹음 파일 임시저장
  - `Implicit Intent`를 활용한 디바이스 파일 시스템 접근

### 평가 생성
  - `MediaRecorder`와 `Coroutine`을 활용한 녹음 기능 제공
  - `ScopedStorage`를 활용한 녹음 파일 임시저장
  - `MediaPlayer`를 활용한 음악 스트리밍
  - `Coroutine`을 활용한 가사 하이라이트 및 음악 구간 탐색

### 평가 확인
  - `MediaPlayer`를 활용한 음성 파일 스트리밍
  - `Coroutine`을 활용한 음악 구간 탐색

<br>

<h2>📱 기타 애플리케이션 기능</h2>

- `Retrofit`을 활용한 서버 통신
- `DownloadManager`를 활용한 커버파일 다운로드
- `Service`, `BroadcastReceiver`, `NotificationChannel`을 활용한 다운로드 알림 표시
- `Implicit Intent`를 활용하여 알림 클릭 시 노래 재생 앱으로 이동

<br>

<h2>🛠️ 프로젝트 기술 스택</h2>

| Title | Content |
| ------------ | -------------------------- |
| UI Framework  | `Jetpack Compose`  |
| Build Tools  | `Gradle Version Catalog` |
| Architecture | `Clean Architecture`, `MVVM` |
| Dependency Injection | `Hilt`  |
| Network | `Retrofit2`, `OkHttp`  |
| Asynchronous Processing | `Coroutine`, `Flow` |
| Other Tools | `MediaRecorder`, `MediaPlayer`  |\

<br>

<h2>👨‍💼 Developer</h2>

| Android Developer |
|:------:|
| <img src="https://avatars.githubusercontent.com/u/101652649?v=4"/> |
| [이석준](https://github.com/boiledEgg-s) |
| 건국대학교 컴퓨터공학부 |

<br>

### ⏭️ [발표 영상 보러가기](https://www.youtube.com/watch?v=_716Th1Tdc4)

</p>
