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

fn greet_people() {
    println!("Hello from Rust!");
}

pub fn entrypoint() {
    App::new()
        // Startup Systems run once at startup
        .add_startup_system(add_persons)
        .add_system(greet_people)
        .run();
}
