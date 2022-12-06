#![allow(clippy::all)]

#[cfg(test)]
mod tests;

use bevy::prelude::*;

pub fn entrypoint() {
    println!("Hello from Rust!");

    App::new().run();
}
