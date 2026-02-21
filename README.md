<div align="center">
  
  <h1>🗂️ Sorted</h1>
  
  <a href="https://git.io/typing-svg">
    <img src="https://readme-typing-svg.herokuapp.com?font=Inter&weight=600&size=22&pause=1000&color=2196F3&center=true&vCenter=true&width=600&lines=Modern+Android+Todo+Application;Clean+Architecture+%2B+Compose;Offline-First+Persistence" alt="Typing SVG" />
  </a>

  <p>
    <img src="https://img.shields.io/badge/Kotlin-Android-blue?style=for-the-badge&logo=kotlin" alt="Kotlin Badge"/>
    <img src="https://img.shields.io/badge/Jetpack_Compose-green?style=for-the-badge&logo=android" alt="Compose Badge"/>
    <img src="https://img.shields.io/badge/Clean_Architecture-orange?style=for-the-badge" alt="Clean Architecture Badge"/>
    <img src="https://img.shields.io/badge/Room_Database-purple?style=for-the-badge" alt="Room Badge"/>
    <img src="https://img.shields.io/badge/MVVM_State-red?style=for-the-badge" alt="MVVM Badge"/>
  </p>

  <br>

  <p align="left">
    <b>Why This Project Exists:</b><br>
    Most Todo apps are just surface-level UI demos. <b>Sorted</b> is built differently. It is an architectural proof-of-concept designed to solve real-world engineering bottlenecks: tightly coupled business logic, unpredictable state mutations, and fragile data synchronization. By strictly enforcing <b>Clean Architecture</b> and <b>Unidirectional Data Flow (UDF)</b>, this codebase demonstrates how to build scalable, highly testable Android applications that are resilient enough for enterprise-level and fintech environments.
  </p>

</div>

<br><hr><br>

## 📌 Project Highlights

<table width="100%">
  <tr>
    <td width="50%" valign="top">
      <h3>✨ Features</h3>
      <ul>
        <li>✔️ <b>Task Management:</b> Create, update, and manage daily tasks.</li>
        <li>✔️ <b>Smart Filtering:</b> Toggle between All, Pending, and Completed.</li>
        <li>✔️ <b>Date Selection:</b> Integrated Material 3 date pickers.</li>
        <li>✔️ <b>Reactive UI:</b> State-driven, fluid updates.</li>
        <li>✔️ <b>Offline-First:</b> Full persistence without internet access.</li>
        <li>✔️ <b>Responsive:</b> Adaptive layouts for varying screen sizes.</li>
      </ul>
    </td>
    <td width="50%" align="center" valign="top">
      <i>()</i><br>
      <img src="https://gemini.google.com/share/f939f81e4009" width="250" alt="App Animation Demo"/>
    </td>
  </tr>
</table>

<br><hr><br>

## 🏗 Architecture & State Flow

<details>
  <summary><b>🖱️ Click to expand: Why Unidirectional Data Flow (UDF)?</b></summary>
  <br>
  <blockquote>
    UI triggers an event ➡️ ViewModel processes logic ➡️ Repository interacts with data ➡️ UI observes state changes and re-renders.
  </blockquote>
  <p>This guarantees lifecycle awareness, reduced UI bugs, and a clear, maintainable event flow. It is the modern standard for Jetpack Compose applications.</p>
</details>

<br>

<table width="100%">
  <tr>
    <td align="center" width="33%">
      <b>🔹 Presentation Layer</b><br>
      Jetpack Compose UI<br>
      Unidirectional Data Flow<br>
      State-driven rendering
    </td>
    <td align="center" width="33%">
      <b>🔹 Domain Layer</b><br>
      Business logic abstraction<br>
      Repository contracts<br>
      Task & filter modeling
    </td>
    <td align="center" width="33%">
      <b>🔹 Data Layer</b><br>
      Room Database<br>
      Repository implementation<br>
      Dependency Injection
    </td>
  </tr>
</table>

<br><hr><br>

## 🎨 UI, Design & Structure

<table width="100%">
  <tr>
    <td width="60%">
      <h3>Design System</h3>
      <ul>
        <li><b>Material 3 Theming:</b> Dynamic, modern aesthetics.</li>
        <li><b>Customization:</b> Tailored typography and color palettes.</li>
        <li><b>Edge-to-Edge:</b> Seamless UI with navigation bar padding support.</li>
      </ul>
      <h3>Scalable Structure</h3>
      <p>The codebase mimics real-world enterprise applications:</p>
      <code><kbd>data/</kbd></code> 
      <code><kbd>domain/</kbd></code> 
      <code><kbd>presentation/</kbd></code> 
      <code><kbd>ui/theme/</kbd></code>
    </td>
    <td width="40%" valign="top">
      <h3>🚀 Getting Started</h3>
      <ol>
        <li>Clone the repository</li>
        <li>Open in <b>Android Studio</b></li>
        <li>Sync Gradle files</li>
        <li>Run on emulator or physical device</li>
      </ol>
    </td>
  </tr>
</table>

<br><hr><br>

## 📈 Roadmap & Future Improvements

<details>
  <summary><b>🖱️ Click to view upcoming features</b></summary>
  <br>
  <ul>
    <li>[ ] Integrate Hilt Dependency Injection</li>
    <li>[ ] Comprehensive Unit & UI Testing</li>
    <li>[ ] Fluid swipe gestures & animations</li>
    <li>[ ] Cloud synchronization capabilities</li>
    <li>[ ] Explicit Dark/Light theme toggle</li>
  </ul>
</details>

<br><hr>

<div align="center">
  <h3>👨‍💻 Developed by Ansh Bhardwaj</h3>
  <p><i>Android Developer • Kotlin Enthusiast • Clean Architecture Advocate</i></p>
  <br>
  <p>⭐ <b>If you found this architecture or project useful, please consider starring the repository!</b> ⭐</p>
</div>
