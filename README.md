# University Tycoon

University Tycoon is a group-developed simulation game built in Java
using LWJGL.\
The project aims to model and simulate the management of a university,
combining real-time rendering with gameplay systems such as resource
management, expansion, and user interaction.

------------------------------------------------------------------------

## Overview

The goal of the project is to create a tycoon-style experience where the
player can build and manage a university environment. This includes
handling infrastructure, resources, and potentially student or
staff-related systems.

The project also explores low-level graphics programming by implementing
a custom rendering pipeline using OpenGL.

------------------------------------------------------------------------

## Features

-   Real-time rendering using OpenGL (via LWJGL)
-   Custom shader system (lighting, shadows, diffuse rendering)
-   Input handling (keyboard and mouse)
-   Window and device management
-   Expandable structure for simulation/game systems

------------------------------------------------------------------------

## Tech Stack

-   **Language:** Java\
-   **Graphics API:** OpenGL\
-   **Library:** LWJGL

------------------------------------------------------------------------

## Project Structure

    src/
    ├── org/lwjgl/...        # LWJGL bindings and system code
    ├── shaders/             # Shader implementations
    │   ├── DiffuseShader.java
    │   ├── LightShader.java
    │   ├── ShadowShader.java
    │   └── ...
    ├── ...                  # Core game / simulation logic

------------------------------------------------------------------------

## Gameplay Direction

The intended gameplay includes:

-   Managing university buildings and layout
-   Allocating resources efficiently
-   Expanding and upgrading facilities
-   Simulating aspects of a university environment

*(Note: Some systems may still be in development depending on the
project stage.)*

------------------------------------------------------------------------

## Getting Started

### Clone the repository

``` bash
git clone https://github.com/YOUR_USERNAME/UniversityTycoon.git
cd UniversityTycoon
```

### Open in IDE

Recommended: - IntelliJ IDEA\
- VS Code (Java extensions)

### Setup

-   Ensure LWJGL dependencies are configured
-   Add native libraries for your OS

### Run

Run the main class to start the application.

------------------------------------------------------------------------

## Current Status

-   ✔ Rendering system implemented\
-   ✔ Shader pipeline functional\
-   ⚠ Simulation/gameplay systems partially implemented\
-   ⚠ UI and interaction systems in progress

------------------------------------------------------------------------

## Future Work

-   Core simulation systems (economy, population, progression)
-   UI and user feedback
-   Improved lighting and visual polish
-   Save/load functionality
-   Performance improvements

------------------------------------------------------------------------

## Development

This project was developed as a **group coursework project** at the
University of York.

------------------------------------------------------------------------

## Authors

-   Thomas Koukouris\
-   Adam Khan\
-   Oliver Heron\
-   Sam Jordan\
-   Nathan Hopper\
-   Fergus Irvine
