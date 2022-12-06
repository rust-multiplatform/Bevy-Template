#![allow(clippy::all)]

#[cfg(test)]
mod tests;

use bevy::prelude::*;

#[derive(Component)]
struct Person;

#[derive(Component)]
struct Name(String);

fn hello_world_system() {
    println!("Hello from Rust!");
}

pub fn entrypoint() {
    App::new().add_system(hello_world_system).run();
}
