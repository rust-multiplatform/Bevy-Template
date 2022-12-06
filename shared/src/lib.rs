#![allow(clippy::all)]

#[cfg(test)]
mod tests;

use bevy::prelude::*;

#[derive(Component)]
struct Person;

#[derive(Component)]
struct Name(String);

fn add_persons(mut commands: Commands) {
    commands.spawn((Person, Name("Someone".to_string())));
    commands.spawn((Person, Name("SomeFirstName SomeLastName".to_string())));
    commands.spawn((Person, Name("Rust".to_string())));
}

fn hello_world_system() {
    println!("Hello from Rust!");
}

pub fn entrypoint() {
    App::new()
        // Startup Systems run once at startup
        .add_startup_system(add_persons)
        .add_system(hello_world_system)
        .run();
}
