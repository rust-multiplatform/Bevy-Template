#![allow(clippy::all)]

#[cfg(test)]
mod tests;

use bevy::prelude::*;

#[derive(Component)]
struct Person;

#[derive(Component)]
struct Name(String);

fn add_persons(mut commands: Commands) {
    commands.spawn((Person, Name("from Rust".to_string())));
    commands.spawn((Person, Name("Someone".to_string())));
    commands.spawn((Person, Name("SomeFirstName SomeLastName".to_string())));
}

fn greet_people(query: Query<&Name, With<Person>>) {
    for person in query.iter() {
        println!("Hello {}!", person.0);
    }
}

pub fn entrypoint() {
    App::new()
        // Startup Systems run once at startup
        .add_startup_system(add_persons)
        .add_system(greet_people)
        .run();
}
